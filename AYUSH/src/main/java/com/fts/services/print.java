package com.fts.services;

/*
 * This applet connect to a URL and send it's data to the local printer
 * specified by param printer_name.
 *
 * How to use it:
 * <applet code="printer" codebase="/" archive="printer.jar"  width="100" height="100" id="printer_id">
 * <param name="url" value="http://foo.bar.com/something.txt"/>
 * <param name="printer_name" value="My Printer"/>
 * </applet>
 *
 */
import java.applet.Applet;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

public class print extends Applet {

	private static final long serialVersionUID = 1L;
	private String printer_name;

  private void prints(InputStream f) throws IOException {
    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
    Doc myDoc = new SimpleDoc(f, flavor, null);
    HashPrintServiceAttributeSet aset = new HashPrintServiceAttributeSet();
    PrintRequestAttributeSet rset = new HashPrintRequestAttributeSet();
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
    PrinterName printer = new PrinterName(this.printer_name, null);
    aset.add(printer);
    services = PrintServiceLookup.lookupPrintServices(null, aset);
    if (services != null) {
      try {
        DocPrintJob job = services[0].createPrintJob();
        job.print(myDoc, rset);
      } catch (ArrayIndexOutOfBoundsException ex) {
        Logger.getLogger(print.class.getName()).log(Level.SEVERE, "Printer not found (printer)", ex);
      } catch (PrintException pe) {
        Logger.getLogger(print.class.getName()).log(Level.SEVERE, null, pe);
      }
    }
  }

  public boolean call_printer(String str_url) {
    try {
      URL remote = new URL(str_url);
      printer_name = getParameter("printer_name");
      URLConnection conn = remote.openConnection();
      conn.setRequestProperty("Accept", "*/*");
      InputStream in = conn.getInputStream();
      prints(in);
    } catch (Exception ex) {
      Logger.getLogger(print.class.getName()).log(Level.SEVERE, "Could not connect to remote host (printer)", ex);
    }
    return true;
  }
}
