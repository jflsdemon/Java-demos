package demon;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.crypto.Mac;
import javax.swing.JOptionPane;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;



public class Main {
	// 定义文件路径 mac ： "/Users/juliang/Public/DemonDownload/"
	//
	public static final String pathStr = "D:" + File.separator + "DownloadCSRC" + File.separator;
	public static final String fileName1 = "csrcperf";
	public static final String fileName2 = "csrcindustry";

	public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] getData = readInputStream(inputStream);

		// 文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

		System.out.println("info:" + url + " download success");

	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			//mac 
			//File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			File file = new File(outPath.substring(0, outPath.lastIndexOf('\\')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		System.out.println("******************解压完毕********************");
	}

	public static String readExcel(File file) {
		String cellinfo = "";
		try {
			// 创建输入流，读取Excel
			InputStream is = new FileInputStream(file.getAbsolutePath());
			// jxl提供的Workbook类
			Workbook wb = Workbook.getWorkbook(is);
			// Excel的页签数量
			int sheet_size = wb.getNumberOfSheets();
			// for (int index = 0; index < sheet_size; index++) {
			// // 每个页签创建一个Sheet对象
			// Sheet sheet = wb.getSheet(index);
			// // sheet.getRows()返回该页的总行数
			// for (int i = 0; i < sheet.getRows(); i++) {
			// // sheet.getColumns()返回该页的总列数
			// for (int j = 0; j < sheet.getColumns(); j++) {
			// String cellinfo = sheet.getCell(j, i).getContents();
			// System.out.println(cellinfo);
			// }
			// }
			// }
			cellinfo = wb.getSheet(0).getCell(0, 1).getContents();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cellinfo;
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static boolean allWorks() throws IOException {
		// 下载zip文件
		try {
			downLoadFromUrl("http://www.csindex.com.cn/uploads/indices/amac/files/csrcperf.zip", fileName1 + ".zip",
					pathStr);
			downLoadFromUrl("http://www.csindex.com.cn/uploads/downloads/other/files/zh_CN/csrcindustry.zip",
					fileName2 + ".zip", pathStr);

		} catch (Exception e) {

		}
		// 解压zip得到xls文件
		File zipFile1 = new File(pathStr + File.separator + fileName1 + ".zip");
		File zipFile2 = new File(pathStr + File.separator + fileName2 + ".zip");
		String path = pathStr;
		unZipFiles(zipFile1, path);
		unZipFiles(zipFile2, path);

		// 读取Excel文件中第1列第2行内容
		File file1 = new File(pathStr + fileName1 + ".xls");
		File file2 = new File(pathStr + fileName2 + ".xls");
		String file1DateTime = readExcel(file1);
		String file2DateTime = readExcel(file2);
		// 与当天日期比较,若就是当天信息，ok；否则，删除，继续
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String curDateTime = df.format(new Date());// new Date()为获取当前系统时间
		if (file1DateTime.equals(curDateTime) && file2DateTime.equals(curDateTime)) {
			//JOptionPane.showConfirmDialog(null, "AMAC基金估值行业分类指数历史行情和证监会行业分类下载完成", "下载完成", JOptionPane.OK_OPTION);
			JOptionPane.showMessageDialog(null, "AMAC基金估值行业分类指数历史行情和证监会行业分类下载完成");
			return false;
		} else {
			delAllFile(pathStr);
			return true;
		}
	}

	static class StartButtonListen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					boolean flag = true;
					while (flag) {
						try {
							flag = allWorks();
							times++;
							textField.setText("Attempts: " + times);
							Thread.sleep(30000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}
	static Frame frame = new Frame("Download task");
	static Button startListen = new Button("start");
	static TextField textField = new TextField("Attempts: 0");
	static int times = 0;
	public static void main(String[] args) throws IOException {
		
		startListen.addActionListener(new StartButtonListen());
		//frame.setLayout(new BorderLayout(100, 100));
		frame.setBounds(150,150,250,200);
		frame.add(startListen, BorderLayout.SOUTH);
		frame.add(textField);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}

}
