package bdv.img.klb;

import net.imglib2.img.basictypeaccess.volatiles.array.VolatileShortArray;

import org.janelia.simview.klb.jni.KlbImageIO;
import org.janelia.simview.klb.jni.KlbRoi;

import bdv.img.cache.CacheArrayLoader;

public class KlbVolatileArrayLoader implements CacheArrayLoader< VolatileShortArray >
{
	private VolatileShortArray theEmptyArray;
	final private String filePath;
    int channel;

	public KlbVolatileArrayLoader( final String filePath, final int channel )
	{
        this.filePath = filePath;
        this.channel = channel;

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

	public VolatileShortArray tryLoadArray(
			final int timepoint,
			final int setup,
			final int level,
			final int[] dimensions,
			final long[] min ) throws InterruptedException
	{
		final short[] data = new short[ dimensions[ 0 ] * dimensions[ 1 ] * dimensions[ 2 ] ];
		final byte[] bytes = new byte[ 2 * dimensions[ 0 ] * dimensions[ 1 ] * dimensions[ 2 ] ];

        final KlbRoi roi = new KlbRoi();
        roi.setXyzctLB( new long[]{ min[0], min[1], min[2], channel, 0 } );
        roi.setXyzctUB( new long[]{
                min[ 0 ] + dimensions[ 0 ] - 1,
                min[ 1 ] + dimensions[ 1 ] - 1,
                min[ 2 ] + dimensions[ 2 ] - 1,
                channel, 0
        } );

        final KlbImageIO io = new KlbImageIO();
        io.setFilename(filePath);
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
