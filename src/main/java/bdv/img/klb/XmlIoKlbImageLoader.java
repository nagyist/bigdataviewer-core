package bdv.img.klb;

import java.io.File;

import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.generic.sequence.ImgLoaderIo;
import mpicbg.spim.data.generic.sequence.XmlIoBasicImgLoader;

import org.jdom2.Element;

@ImgLoaderIo( format = "klb", type = KlbImageLoader.class )
public class XmlIoKlbImageLoader implements XmlIoBasicImgLoader< KlbImageLoader >
{
	@Override
	public Element toXml( final KlbImageLoader imgLoader, final File basePath )
	{
		throw new UnsupportedOperationException( "not implemented" );
	}

	@Override
	public KlbImageLoader fromXml( final Element elem, final File basePath, final AbstractSequenceDescription< ?, ?, ? > sequenceDescription )
	{
		final String filePath = elem.getChildText( "filePath" );
		final int channel = Integer.parseInt( elem.getChildText( "channel" ) );
		return new KlbImageLoader( filePath, channel );
	}
}
