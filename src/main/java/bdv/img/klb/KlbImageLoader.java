package bdv.img.klb;

import java.util.Arrays;

import mpicbg.spim.data.sequence.ViewId;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.NativeImg;
import net.imglib2.img.basictypeaccess.volatiles.array.VolatileShortArray;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.volatiles.VolatileARGBType;
import net.imglib2.type.volatiles.VolatileUnsignedShortType;
import net.imglib2.util.Fraction;

import org.janelia.simview.klb.jni.KlbImageHeader;
import org.janelia.simview.klb.jni.KlbImageIO;

import bdv.AbstractViewerImgLoader;
import bdv.img.cache.Cache;
import bdv.img.cache.CacheHints;
import bdv.img.cache.CachedCellImg;
import bdv.img.cache.LoadingStrategy;
import bdv.img.cache.VolatileGlobalCellCache;
import bdv.img.cache.VolatileImgCells;
import bdv.img.cache.VolatileImgCells.CellCache;

public class KlbImageLoader extends AbstractViewerImgLoader< UnsignedShortType, VolatileUnsignedShortType >
{
	private final int numScales;

	private final double[][] mipmapResolutions;

	private final long[][] imageDimensions;

	private final int[][] blockDimensions;

	private final AffineTransform3D[] mipmapTransforms;

	protected VolatileGlobalCellCache< VolatileShortArray > cache;

	public KlbImageLoader( final String filePath, final int channel )
	{
	    super( new UnsignedShortType(), new VolatileUnsignedShortType() );

		numScales = 1;

		final KlbImageIO io = new KlbImageIO();
		io.setFilename(filePath);
		io.readHeader();

		final KlbImageHeader header = io.getHeader();

		final long[] blockSizes = header.getBlockSize();
		blockDimensions = new int[][]{ {
		    ( int )blockSizes[0],
		    ( int )blockSizes[1],
		    ( int )blockSizes[2] } };

		final long[] xyzct = header.getXyzct();
		imageDimensions = new long[][]{ { xyzct[ 0 ], xyzct[ 1 ], xyzct[ 2 ] } };

		final float[] pixelSize = header.getPixelSize();
		mipmapResolutions = new double[][]{ {
            // handle missing sampling metadata (defaults to -1)
		    pixelSize[ 0 ] != -1 ? pixelSize[0] : 1,
		    pixelSize[ 1 ] != -1 ? pixelSize[1] : 1,
		    pixelSize[ 2 ] != -1 ? pixelSize[2] : 1
        } };

		System.out.println( Arrays.toString( pixelSize ) );

		mipmapTransforms = new AffineTransform3D[]{
		        new AffineTransform3D()
		};
		mipmapTransforms[0].set(
		        1, 0, 0, 0,
		        0, mipmapResolutions[ 0 ][ 0 ] / mipmapResolutions[ 0 ][ 1 ], 0, 0,
		        0, 0, mipmapResolutions[ 0 ][ 0 ] / mipmapResolutions[ 0 ][ 2 ], 0
        );

		cache = new VolatileGlobalCellCache< VolatileShortArray >(
				new KlbVolatileArrayLoader( filePath, channel ), 1, 1, numScales, 10 );
	}


	@Override
	public RandomAccessibleInterval< UnsignedShortType > getImage( final ViewId view, final int level )
	{
		final CachedCellImg< UnsignedShortType, VolatileShortArray > img = prepareCachedImage( view, level, LoadingStrategy.BLOCKING );
		final UnsignedShortType linkedType = new UnsignedShortType( img );
		img.setLinkedType( linkedType );
		return img;
	}

	@Override
	public RandomAccessibleInterval< VolatileUnsignedShortType > getVolatileImage( final ViewId view, final int level )
	{
		final CachedCellImg< VolatileUnsignedShortType, VolatileShortArray > img = prepareCachedImage( view, level, LoadingStrategy.VOLATILE );
		final VolatileUnsignedShortType linkedType = new VolatileUnsignedShortType( img );
		img.setLinkedType( linkedType );
		return img;
	}

	@Override
	public double[][] getMipmapResolutions( final int setup )
	{
		return mipmapResolutions;
	}

	@Override
	public int numMipmapLevels( final int setup )
	{
		return numScales;
	}

	/**
	 * (Almost) create a {@link CachedCellImg} backed by the cache. The created image
	 * needs a {@link NativeImg#setLinkedType(net.imglib2.type.Type) linked
	 * type} before it can be used. The type should be either {@link ARGBType}
	 * and {@link VolatileARGBType}.
	 */
	protected < T extends NativeType< T > > CachedCellImg< T, VolatileShortArray > prepareCachedImage( final ViewId view, final int level, final LoadingStrategy loadingStrategy )
	{
		final long[] dimensions = imageDimensions[ level ];
		final int[] cellDimensions = blockDimensions[ level ];

		final int priority = numScales - 1 - level;
		final CacheHints cacheHints = new CacheHints( loadingStrategy, priority, false );
		final CellCache< VolatileShortArray > c = cache.new VolatileCellCache( view.getTimePointId(), view.getViewSetupId(), level, cacheHints );
		final VolatileImgCells< VolatileShortArray > cells = new VolatileImgCells< VolatileShortArray >( c, new Fraction(), dimensions, cellDimensions );
		final CachedCellImg< T, VolatileShortArray > img = new CachedCellImg< T, VolatileShortArray >( cells );
		return img;
	}

	@Override
	public Cache getCache()
	{
		return cache;
	}

	@Override
	public AffineTransform3D[] getMipmapTransforms( final int setup )
	{
		return mipmapTransforms;
	}
}
