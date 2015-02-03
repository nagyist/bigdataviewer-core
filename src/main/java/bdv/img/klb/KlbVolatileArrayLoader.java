package bdv.img.klb;

import net.imglib2.img.basictypeaccess.volatiles.array.VolatileShortArray;

import org.janelia.simview.klb.jni.Klb;
import org.janelia.simview.klb.jni.KlbImageIO;
import org.janelia.simview.klb.jni.KlbRoi;

import bdv.img.cache.CacheArrayLoader;

public class KlbVolatileArrayLoader implements CacheArrayLoader< VolatileShortArray >
{
	private VolatileShortArray theEmptyArray;
	final private long[] klb_min = new long[ Klb.KLB_DATA_DIMS ];
	final private long[] klb_max = new long[ Klb.KLB_DATA_DIMS ];
	final private String filePath;
//	final private KlbImageIO io = new KlbImageIO();
//	final private KlbRoi roi = new KlbRoi();

	public KlbVolatileArrayLoader( final String filePath, final int channel )
	{
        this.filePath = filePath;

	    klb_min[ 3 ] = klb_max[ 3 ] = channel;
	    theEmptyArray = new VolatileShortArray( 1, false );
	}

	@Override
	public int getBytesPerElement()
	{
		return 2;
	}

	@Override
	public VolatileShortArray loadArray(
			final int timepoint,
			final int setup,
			final int level,
			final int[] dimensions,
			final long[] min ) throws InterruptedException
	{
		try
		{
			return tryLoadArray( timepoint, setup, level, dimensions, min );
		}
		catch ( final OutOfMemoryError e )
		{
			System.gc();
			return tryLoadArray( timepoint, setup, level, dimensions, min );
		}
	}

	public synchronized VolatileShortArray tryLoadArray(
			final int timepoint,
			final int setup,
			final int level,
			final int[] dimensions,
			final long[] min ) throws InterruptedException
	{
	    final KlbImageIO io = new KlbImageIO();
        final KlbRoi roi = new KlbRoi();
        io.setFilename( filePath );
        io.setNumThreads(1);

		final short[] data = new short[ dimensions[ 0 ] * dimensions[ 1 ] * dimensions[ 2 ] ];
		final byte[] bytes = new byte[ 2 * dimensions[ 0 ] * dimensions[ 1 ] * dimensions[ 2 ] ];

        klb_min[ 0 ] = min[ 0 ];
        klb_min[ 1 ] = min[ 1 ];
        klb_min[ 2 ] = min[ 2 ];

        klb_max[ 0 ] = min[ 0 ] + dimensions[ 0 ] - 1;
        klb_max[ 1 ] = min[ 1 ] + dimensions[ 1 ] - 1;
        klb_max[ 2 ] = min[ 2 ] + dimensions[ 2 ] - 1;

        io.readHeader();

        roi.setXyzctLB( klb_min );
        roi.setXyzctUB( klb_max );
        io.readImage( bytes, roi, 1 );

        for ( int i = 0, j = -1; i < data.length; ++i )
        {
            data[ i ] = ( short )( ( bytes[ ++j ] & 0xff ) | ( ( bytes[ ++j ] & 0xff ) << 8 ) );
        }

        return new VolatileShortArray( data, true );
	}

	@Override
	public VolatileShortArray emptyArray( final int[] dimensions )
	{
		int numEntities = 1;
		for ( int i = 0; i < dimensions.length; ++i )
			numEntities *= dimensions[ i ];
		if ( theEmptyArray.getCurrentStorageArray().length < numEntities )
			theEmptyArray = new VolatileShortArray( numEntities, false );
		return theEmptyArray;
	}
}
