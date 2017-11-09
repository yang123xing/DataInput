package com.yangxing.stock.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {

	private static String myStr = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/";

	public static void main(String[] args) throws HttpException, IOException {

		getData("600000", "2016", "2");
	}

	public static void getData(String code, String year, String jidu) throws HttpException, IOException {

		CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
		CloseableHttpResponse response = null;

		String url = myStr + code + ".phtml?year=" + year + "&jidu=" + jidu;

		String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
		HttpHost proxy = new HttpHost("10.36.160.1", 80);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", userAgent);
		httpGet.setConfig(config);
		response = httpClient.execute(httpGet);
		System.out.println(response.getStatusLine().getStatusCode() + "------");

		String responseContent = EntityUtils.toString(response.getEntity(), "UTF-8");
		String reg = "(?<=(date=)).*?(?=('>))|(?<=(\"center\">))\\d{1}.*?(?=(</div>))";
		List<String> ls = new ArrayList<String>();
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(responseContent);
		while (matcher.find()) {// 开 最 收 低 量 额
			ls.add(matcher.group());
		}
		// 将数据写入文件
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(new File("F://123.txt")));
			int length = ls.size() / 7;
			for (int k = 0; k < length; k++) {

				for (int i = 0; i < 7; i++) {

					writer.write(ls.get(i + k * 7));
					if (i < 6) {

						writer.write(",");
					}

				}
				writer.newLine();
				writer.flush();
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			if (null != writer) {

				try {
					writer.close();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		}

		String sql = "insert into base_data (code, name,date, start,height,end,low,volume, money) values (?, ?, ?, ?, ?, ?, ?, ?,?)";
		Connection connection = null;
		PreparedStatement ps = null;
		try {

			connection = new DBHelper().getConnection();
			ps = connection.prepareStatement(sql);

			int length = ls.size() / 7;
			for (int k = 0; k < length; k++) {

				ps.setString(1, code);
				ps.setString(2, "");
				ps.setString(3, ls.get(0 + 7 * k));
				ps.setString(4, ls.get(1 + 7 * k));
				ps.setString(5, ls.get(2 + 7 * k));
				ps.setString(6, ls.get(3 + 7 * k));
				ps.setString(7, ls.get(4 + 7 * k));
				ps.setString(8, ls.get(5 + 7 * k));
				ps.setString(9, ls.get(6 + 7 * k));
				ps.addBatch();
			}
			ps.executeBatch();
			System.out.println("success");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (ps != null) {

				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

}
