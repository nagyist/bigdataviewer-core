/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.janelia.simview.klb.jni;

public class KlbJNI {
  public final static native int KLB_DATA_DIMS_get();
  public final static native void KlbImageHeader_xyzct_set(long jarg1, KlbImageHeader jarg1_, long[] jarg2);
  public final static native long[] KlbImageHeader_xyzct_get(long jarg1, KlbImageHeader jarg1_);
  public final static native void KlbImageHeader_pixelSize_set(long jarg1, KlbImageHeader jarg1_, float[] jarg2);
  public final static native float[] KlbImageHeader_pixelSize_get(long jarg1, KlbImageHeader jarg1_);
  public final static native void KlbImageHeader_dataType_set(long jarg1, KlbImageHeader jarg1_, short jarg2);
  public final static native short KlbImageHeader_dataType_get(long jarg1, KlbImageHeader jarg1_);
  public final static native void KlbImageHeader_compressionType_set(long jarg1, KlbImageHeader jarg1_, short jarg2);
  public final static native short KlbImageHeader_compressionType_get(long jarg1, KlbImageHeader jarg1_);
  public final static native void KlbImageHeader_blockSize_set(long jarg1, KlbImageHeader jarg1_, long[] jarg2);
  public final static native long[] KlbImageHeader_blockSize_get(long jarg1, KlbImageHeader jarg1_);
  public final static native long new_KlbImageHeader__SWIG_0(long jarg1, KlbImageHeader jarg1_);
  public final static native void delete_KlbImageHeader(long jarg1);
  public final static native long new_KlbImageHeader__SWIG_1();
  public final static native int KlbImageHeader_readHeader(long jarg1, KlbImageHeader jarg1_, String jarg2);
  public final static native long KlbImageHeader_getNumBlocks(long jarg1, KlbImageHeader jarg1_);
  public final static native long KlbImageHeader_calculateNumBlocks(long jarg1, KlbImageHeader jarg1_);
  public final static native long KlbImageHeader_getSizeInBytes(long jarg1, KlbImageHeader jarg1_);
  public final static native long KlbImageHeader_getSizeInBytesFixPortion(long jarg1, KlbImageHeader jarg1_);
  public final static native long KlbImageHeader_getBytesPerPixel(long jarg1, KlbImageHeader jarg1_);
  public final static native long KlbImageHeader_getBlockSizeBytes(long jarg1, KlbImageHeader jarg1_);
  public final static native java.math.BigInteger KlbImageHeader_getImageSizeBytes(long jarg1, KlbImageHeader jarg1_);
  public final static native java.math.BigInteger KlbImageHeader_getImageSizePixels(long jarg1, KlbImageHeader jarg1_);
  public final static native long KlbImageHeader_getBlockCompressedSizeBytes(long jarg1, KlbImageHeader jarg1_, long jarg2);
  public final static native java.math.BigInteger KlbImageHeader_getBlockOffset(long jarg1, KlbImageHeader jarg1_, long jarg2);
  public final static native java.math.BigInteger KlbImageHeader_getCompressedFileSizeInBytes(long jarg1, KlbImageHeader jarg1_);
  public final static native void KlbImageHeader_setDefaultBlockSize(long jarg1, KlbImageHeader jarg1_);
  public final static native void KlbRoi_xyzctLB_set(long jarg1, KlbRoi jarg1_, long[] jarg2);
  public final static native long[] KlbRoi_xyzctLB_get(long jarg1, KlbRoi jarg1_);
  public final static native void KlbRoi_xyzctUB_set(long jarg1, KlbRoi jarg1_, long[] jarg2);
  public final static native long[] KlbRoi_xyzctUB_get(long jarg1, KlbRoi jarg1_);
  public final static native void KlbRoi_defineSlice(long jarg1, KlbRoi jarg1_, int jarg2, int jarg3, long[] jarg4);
  public final static native void KlbRoi_defineFullImage(long jarg1, KlbRoi jarg1_, long[] jarg2);
  public final static native long KlbRoi_getSizePixels__SWIG_0(long jarg1, KlbRoi jarg1_, int jarg2);
  public final static native java.math.BigInteger KlbRoi_getSizePixels__SWIG_2(long jarg1, KlbRoi jarg1_);
  public final static native long new_KlbRoi();
  public final static native void delete_KlbRoi(long jarg1);
  public final static native void KlbImageIO_filename_set(long jarg1, KlbImageIO jarg1_, String jarg2);
  public final static native String KlbImageIO_filename_get(long jarg1, KlbImageIO jarg1_);
  public final static native void KlbImageIO_header_set(long jarg1, KlbImageIO jarg1_, long jarg2, KlbImageHeader jarg2_);
  public final static native long KlbImageIO_header_get(long jarg1, KlbImageIO jarg1_);
  public final static native void KlbImageIO_numThreads_set(long jarg1, KlbImageIO jarg1_, int jarg2);
  public final static native int KlbImageIO_numThreads_get(long jarg1, KlbImageIO jarg1_);
  public final static native long new_KlbImageIO__SWIG_0();
  public final static native long new_KlbImageIO__SWIG_1(String jarg1);
  public final static native int KlbImageIO_readHeader__SWIG_0(long jarg1, KlbImageIO jarg1_);
  public final static native int KlbImageIO_readHeader__SWIG_1(long jarg1, KlbImageIO jarg1_, String jarg2);
  public final static native int KlbImageIO_writeImage(long jarg1, KlbImageIO jarg1_, byte[] jarg2, int jarg3);
  public final static native int KlbImageIO_readImage(long jarg1, KlbImageIO jarg1_, byte[] jarg2, long jarg3, KlbRoi jarg3_, int jarg4);
  public final static native int KlbImageIO_readImageFull(long jarg1, KlbImageIO jarg1_, byte[] jarg2, int jarg3);
  public final static native void delete_KlbImageIO(long jarg1);
}
