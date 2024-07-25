package com.fts.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fts.components.CustomComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.GridDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class BarCodeService implements GridComponent,CustomComboComponent{
	
	private static final Log LOG = LogFactory.getLog(BarCodeService.class);
	
	 @Autowired
	 private Properties pathConfig;
	 
//-------------------------------------------------------------------------------------------------------------------------------------------	
//	Bar Code Generate Method By PDF
	public synchronized String genrateBarCode(String str) throws FileNotFoundException, DocumentException{
		try{
		 
			    //Document document = new Document(new Rectangle(PageSize.A7));
				Document document = new Document();
		   		document.setPageSize(new Rectangle(54f,22f));
		   		
		   		document.setMargins(2, 0, 4, 0);
		        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathConfig.getProperty("barcodePath")+"//Fts_BarCode_128.pdf"));    
		        document.open();
		        Barcode128 shipBarCode = new Barcode128();
		        shipBarCode.setX(0.3f);
		        shipBarCode.setN(0.3f);
		        shipBarCode.setChecksumText(true);
		        shipBarCode.setGenerateChecksum(true);
		        shipBarCode.setSize(3f);
		        shipBarCode.setTextAlignment(Element.ALIGN_CENTER);
		        shipBarCode.setBaseline(2.6f);
		        shipBarCode.setCode(str);
		        shipBarCode.setAltText("FTS "+str);
		        shipBarCode.setBarHeight(8f);
		        document.add(shipBarCode.createImageWithBarcode(writer.getDirectContent(), null, null));
		        document.close();
		    LOG.info("Bar Code Generated...!!!!!!");
		return str;
		}catch(Exception ex){
			LOG.info("Exception while submitting the proposal :"+ex.getMessage(),ex);
			return "";
		}
	}
	@Override
	public List<?> getCustomComboData(String... extraParams) {
		return null;
	}

	@Override
	public GridDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit,
			String... extraParams) throws Exception {
		return null;
	}
}
