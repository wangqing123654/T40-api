package com.dongyang.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志方法
 * @author lix
 *
 */
public class FileLogUtils {
	private static boolean isDebug = true;
	  public static DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	  public static synchronized boolean moveDir(String sourceFilePath, String targetDirPath, String newFileName)
	  {
	    try
	    {
	      File sourceFile = new File(sourceFilePath);
	      File targetDir = new File(targetDirPath);

	      if (!targetDir.exists()) {
	        targetDir.mkdirs();
	      }

	      File fnew = new File(targetDir.getPath() + File.separator + 
	        newFileName);

	      return sourceFile.renameTo(fnew);
	    }
	    catch (Exception e)
	    {
	      if (isDebug)
	        e.printStackTrace();
	    }
	    return false;
	  }

	  public static boolean forceDelete(File file)
	  {
	    if ((file == null) || (!file.exists())) {
	      return false;
	    }
	    boolean success = file.delete();
	    return success;
	  }

	  public static synchronized boolean copyFile(String src, String dest)
	  {
	    FileInputStream in = null;
	    FileOutputStream out = null;
	    try {
	      File sourceFile = new File(src);
	      File targetDir = new File(dest);
	      in = new FileInputStream(sourceFile);
	      out = new FileOutputStream(targetDir);
	      byte[] buffer = new byte[1024];
	      while (in.read(buffer) != -1) {
	        out.write(buffer);
	      }

	      return true;
	    }
	    catch (Exception e)
	    {
	      return false;
	    } finally {
	      try {
	        if (in != null) {
	          in.close();
	        }
	        if (out != null)
	          out.close();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }

	  public static void appendLog(String fileName, String content)
	  {
	    FileWriter fw = null;
	    try {
	      fw = new FileWriter(fileName, true);
	      fw.write(df.format(new Date()) + "->" + content + "\r\n");
	    }
	    catch (IOException localIOException)
	    {
	      if (fw != null)
	        try {
	          fw.close();
	        }
	        catch (IOException localIOException1)
	        {
	        }
	    }
	    finally
	    {
	      if (fw != null)
	        try {
	          fw.close();
	        }
	        catch (IOException localIOException2)
	        {
	        }
	    }
	  }
}
