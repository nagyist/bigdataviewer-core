package viewer;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.realtransform.AffineTransform3D;

/**
 * Provides image data for all time-points of one view setup.
 *
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 */
public interface SpimAngleSource< T >
{
	/**
	 * Is there a stack at timepoint t?
	 *
	 * @param t
	 *            timepoint index
	 * @return true, if there is data for timepoint t.
	 */
	public boolean isPresent( int t );

	/**
	 * Get the 3D stack at timepoint t.
	 *
	 * @param t
	 *            timepoint index
	 * @return the {@link RandomAccessibleInterval stack}.
	 */
	public RandomAccessibleInterval< T > getSource( int t );

	/**
	 * Get the transform from the {@link #getSource(long) source} at timepoint t
	 * into the viewer coordinate system.
	 *
	 * @param t
	 *            timepoint index
	 * @return transforms source into the viewer coordinate system.
	 */
	public AffineTransform3D getSourceTransform( int t );

	public String getName();
}