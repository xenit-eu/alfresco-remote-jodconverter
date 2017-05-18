/**
 * 
 */
package eu.xenit.transformers.content.transform;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.crypto.dsig.TransformException;

import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.transform.AbstractContentTransformer2;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.TransformationOptions;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This transformer relies on a remote JODConverter for office
 * documents transformations.
 *
 * @author <a href="mailto:younes.regaieg@xenit.eu">Younes REGAIEG</a>
 */
public class RemoteJODConverterContentTransformer extends AbstractContentTransformer2 {

	private static final String JODCONVERTER_ENDPOINT_KEY = "content.transformer.RemoteJODConverter.endpoint";
	private static final String DEFAULT_JODCONVERTER_ENDPOINT = "https://jodconverter.dev.xenit.eu/converter/service";
	private static final Log logger = LogFactory.getLog(RemoteJODConverterContentTransformer.class);
	private static final List<String> TARGET_MIMETYPES = Arrays
			.asList(new String[] { MimetypeMap.MIMETYPE_TEXT_PLAIN, MimetypeMap.MIMETYPE_PDF });

	private static final double MEGABYTES = 1024.0 * 1024.0;

	private static final String USAGE_PATTERN = "Content transformation has completed:\n" + "    Transformer: %s\n"
			+ "    Content Reader: %s\n" + "    Memory (MB): Used/Total/Maximum - %f/%f/%f\n" + "    Time Spent: %d ms";

	public static final String MIMETYPE_APPLICATION_RTF = "application/rtf";
	public static final String MIMETYPE_APPLICATION_X_RTF = "application/x-rtf";
	public static final String MIMETYPE_TEXT_RICHTEXT = "text/richtext";
	private static final List<String> SOURCE_MIMETYPES = Arrays.asList(new String[] {
			MimetypeMap.MIMETYPE_OPENDOCUMENT_PRESENTATION, MimetypeMap.MIMETYPE_OPENDOCUMENT_SPREADSHEET,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_TEXT, MimetypeMap.MIMETYPE_OPENDOCUMENT_GRAPHICS,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_IMAGE, MimetypeMap.MIMETYPE_OPENDOCUMENT_FORMULA,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_PRESENTATION_TEMPLATE,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_SPREADSHEET_TEMPLATE, MimetypeMap.MIMETYPE_OPENDOCUMENT_TEXT_TEMPLATE,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_TEXT_MASTER, MimetypeMap.MIMETYPE_OPENDOCUMENT_TEXT_WEB,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_GRAPHICS_TEMPLATE, MimetypeMap.MIMETYPE_OPENDOCUMENT_IMAGE_TEMPLATE,
			MimetypeMap.MIMETYPE_OPENDOCUMENT_FORMULA_TEMPLATE, MimetypeMap.MIMETYPE_OPENXML_PRESENTATION,
			MimetypeMap.MIMETYPE_OPENXML_SPREADSHEET, MimetypeMap.MIMETYPE_OPENXML_WORDPROCESSING,
			MimetypeMap.MIMETYPE_OPENXML_WORD_TEMPLATE, MimetypeMap.MIMETYPE_OPENOFFICE1_WRITER,
			MimetypeMap.MIMETYPE_OPENOFFICE1_IMPRESS, MimetypeMap.MIMETYPE_WORD, MimetypeMap.MIMETYPE_PPT,
			MimetypeMap.MIMETYPE_TEXT_PLAIN, MIMETYPE_APPLICATION_RTF, MIMETYPE_APPLICATION_X_RTF, MIMETYPE_TEXT_RICHTEXT,
			MimetypeMap.MIMETYPE_HTML, MimetypeMap.MIMETYPE_XHTML,
			MimetypeMap.MIMETYPE_TEXT_PLAIN,
			MimetypeMap.MIMETYPE_EXCEL });

	protected Properties properties;


	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Can we do the requested transformation via remote JODConverter? We
	 * support transforming office documents to PDF or Text
	 */
	@Override
	public boolean isTransformableMimetype(String sourceMimetype, String targetMimetype,
			TransformationOptions options) {
		logger.debug("Checking if can transform '"+sourceMimetype+"' to '"+targetMimetype+"' ...");
		logger.debug("Transformation options :" + options.toString());

		if (!SOURCE_MIMETYPES.contains(sourceMimetype)) {
			// The source isn't one of ours
			return false;
		}

		if (TARGET_MIMETYPES.contains(targetMimetype)) {
			// We can output to this
			return true;
		}
		// We support the source, but not the target
		return false;
	}

	@Override
	public String getComments(boolean available) {
		return getCommentsOnlySupports(SOURCE_MIMETYPES, TARGET_MIMETYPES, available);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.alfresco.repo.content.transform.AbstractContentTransformer2#
	 * transformInternal(org.alfresco.service.cmr.repository.ContentReader,
	 * org.alfresco.service.cmr.repository.ContentWriter,
	 * org.alfresco.service.cmr.repository.TransformationOptions)
	 */
	@Override
	protected void transformInternal(ContentReader reader, ContentWriter writer, TransformationOptions options)
			throws Exception {

		OutputStream os = writer.getContentOutputStream();
		String encoding = writer.getEncoding();
		String targetMimeType = writer.getMimetype();
		String sourceMimeType = reader.getMimetype();

		Writer ow = new OutputStreamWriter(os, encoding);

		InputStream is = null;

		long startTime = 0;
		try {
			is = reader.getContentInputStream();
			if (logger.isDebugEnabled()) {
				startTime = System.currentTimeMillis();
			}

			String url = (properties.containsKey(JODCONVERTER_ENDPOINT_KEY) ? properties.getProperty(JODCONVERTER_ENDPOINT_KEY)    : DEFAULT_JODCONVERTER_ENDPOINT);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// Set up limits -- TODO check if these values are taken into
			// consideration? I am getting the feeling timeouts are handled
			// on the dedicated thread for this transformation
			long readLimitTimeMs = options.getReadLimitTimeMs();
			if (readLimitTimeMs != -1)
				con.setConnectTimeout((int) readLimitTimeMs);
			long timeoutMs = options.getTimeoutMs();
			if (timeoutMs != -1)
				con.setReadTimeout((int) timeoutMs);

			// add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", sourceMimeType);
			con.setRequestProperty("Accept", targetMimeType);

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());

			// FIXME add support for 2GB+ content... Really ?
			IOUtils.copy(is, wr);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				// Something went extremely wrong on remote server
				logger.error("Remote transformation failed, remote host returned response code :" + responseCode);
				if (logger.isDebugEnabled()) {
					logger.debug("Source MimeType : " + sourceMimeType);
					logger.debug("Target MimeType : " + targetMimeType);
					logger.debug("Source size : " + reader.getSize());
					logger.debug("Source ContentURL : " + reader.getContentUrl());
					logger.debug("Remote JODConverter instance : " + url);
				}
				throw new TransformException(con.getResponseMessage());
			}

			IOUtils.copy(con.getInputStream(), os);
		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug(calculateMemoryAndTimeUsage(reader, startTime));
			}

			if (is != null) {
				try {
					is.close();
				} catch (Throwable e) {
				}
			}
			if (ow != null) {
				try {
					ow.close();
				} catch (Throwable e) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (Throwable e) {
				}
			}
		}
	}

	private String calculateMemoryAndTimeUsage(ContentReader reader, long startTime) {
		long endTime = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		long totalMemory = runtime.totalMemory();
		return String.format(USAGE_PATTERN, this.getClass().getName(), reader,
				(totalMemory - runtime.freeMemory()) / MEGABYTES, totalMemory / MEGABYTES,
				runtime.maxMemory() / MEGABYTES, (endTime - startTime));
	}

}
