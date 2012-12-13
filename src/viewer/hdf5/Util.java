package viewer.hdf5;

import mpicbg.tracking.data.View;

public class Util
{
	final static private String groupFormatString = "t%05d/s%02d/%d";

	final static private String cellsFormatString = "%s/cells";

	public static String getGroupPath( final int timepoint, final int setup, final int level )
	{
		return String.format( groupFormatString, timepoint, setup, level );
	}

	public static String getGroupPath( final View view, final int level )
	{
		return String.format( groupFormatString, view.getTimepointIndex(), view.getSetupIndex(), level );
	}

	public static String getCellsPath( final int timepoint, final int setup, final int level )
	{
		return String.format( cellsFormatString, getGroupPath( timepoint, setup, level ) );
	}

	public static String getCellsPath( final View view, final int level )
	{
		return String.format( cellsFormatString, getGroupPath( view, level ) );
	}

	/**
	 * Reorder long array representing column-major coordinate (imglib2) to
	 * row-major (hdf5). Permuted in is stored in out and out is returned.
	 *
	 * @param in
	 *            column major coordinates
	 * @param out
	 *            row major coordinates
	 * @return out
	 */
	public static long[] reorder( final long[] in, final long[] out )
	{
		assert in.length == out.length;
		for ( int i = 0, o = in.length - 1; i < in.length; ++i, --o )
			out[ o ] = in[ i ];
		return out;
	}

	/**
	 * Reorder long array representing column-major coordinate (imglib2) to
	 * row-major (hdf5).
	 *
	 * @param in
	 *            column major coordinates
	 * @return row major coordinates (new array).
	 */
	public static long[] reorder( final long[] in )
	{
		return reorder( in, new long[ in.length ] );
	}

	/**
	 * Reorder long array representing column-major coordinate (imglib2) to
	 * row-major (hdf5). Permuted in is stored in out and out is returned.
	 *
	 * @param in
	 *            column major coordinates
	 * @param out
	 *            row major coordinates
	 * @return out
	 */
	public static int[] reorder( final int[] in, final int[] out )
	{
		assert in.length == out.length;
		for ( int i = 0, o = in.length - 1; i < in.length; ++i, --o )
			out[ o ] = in[ i ];
		return out;
	}

	/**
	 * Reorder long array representing column-major coordinate (imglib2) to
	 * row-major (hdf5).
	 *
	 * @param in
	 *            column major coordinates
	 * @return row major coordinates (new array).
	 */
	public static int[] reorder( final int[] in )
	{
		return reorder( in, new int[ in.length ] );
	}

	public static Timer timer;

	public static class Timer
	{
		private long time;

		public Timer()
		{
			time = System.nanoTime();
			totalTime = 0;
			ioTime = 0;
			byteCount = 0;
		}

		public long nanoTime()
		{
			final long t = System.nanoTime();
			if ( t > time )
				time = t;
			return time;
		}

		private long totalTime;

		private long currentTotal;

		public void start()
		{
			currentTotal = nanoTime();
		}

		public void stop()
		{
			totalTime += nanoTime() - currentTotal;
		}

		private long ioTime;

		private long currentIO;

		public void startIO()
		{
			currentIO = nanoTime();
		}

		public void stopIO()
		{
			ioTime += (nanoTime() - currentIO);
		}

		private long byteCount;

		public void addIoBytes( final long n )
		{
			byteCount += n;
		}

		public long getTotalTime()
		{
			return totalTime;
		}

		public long getIoTime()
		{
			return ioTime;
		}

		public long getIoBytes()
		{
			return byteCount;
		}
	}
}
