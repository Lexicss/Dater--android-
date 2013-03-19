package by.lex.dater;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import by.lex.dater.DateInfo.DatePoint;

public class MainActivity extends Activity {
	private static String l = "myLogs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		double jd;

		jd = DateInfo.getEncodeJD(-4713, 11, 25, 0, 0);
		Log.d(l, "8.11.-4712 JD is " + jd);
		jd = DateInfo.getEncodeJD(2013, 3, 18, 21, 13, 48);
		Log.d(l, " day for 18.03.2013 JD is " + jd);

		Date d = DateInfo.getDecodeJD(2456290.1);
		String formatString = "dd.MM.yyyy, HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.getDefault());
		Log.d(l, "date is " + sdf.format(d));

		/*
		 * String format = "yyyy-MM-dd";
		 * SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		 * etDate.setText(sdf.format(d));
		 */
		DatePoint siriusDP = DateInfo.convertRA(6.75);
		DatePoint arcturDP = DateInfo.convertRA(14.25);
		DatePoint vegaDP = DateInfo.convertRA(18.62);
		DatePoint capellaDP = DateInfo.convertRA(5.27);
		DatePoint rigelDP = DateInfo.convertRA(5.23);
		DatePoint procionDP = DateInfo.convertRA(7.64);

		DatePoint betelgeizeDP = DateInfo.convertRA(5.92);
		DatePoint altairDP = DateInfo.convertRA(19.91);
		DatePoint antaresDP = DateInfo.convertRA(16.48);
		DatePoint spikaDP = DateInfo.convertRA(13.4);

		DatePoint polluksDP = DateInfo.convertRA(7.75);
		DatePoint fomalgautDP = DateInfo.convertRA(22.94);

		DatePoint denebDP = DateInfo.convertRA(20.69);

		DatePoint regulDP = DateInfo.convertRA(10.14);
		DatePoint adaraDP = DateInfo.convertRA(6.96);
		DatePoint castorDP = DateInfo.convertRA(7.575);

		Log.i(l, "Sirius - " + siriusDP.getTimeString());
		Log.i(l, "Arctur - " + arcturDP.getTimeString());
		Log.i(l, "Vega - " + vegaDP.getTimeString());
		Log.i(l, "Capella - " + capellaDP.getTimeString());

		Log.i(l, "Rigel - " + rigelDP.getTimeString());
		Log.i(l, "Procion - " + procionDP.getTimeString());
		Log.i(l, "Betelgeize - " + betelgeizeDP.getTimeString());
		Log.i(l, "Altair - " + altairDP.getTimeString());
		Log.i(l, "Antares - " + antaresDP.getTimeString());
		Log.i(l, "Spika - " + spikaDP.getTimeString());

		Log.i(l, "Polluks - " + polluksDP.getTimeString());
		Log.i(l, "Fomalgaut - " + fomalgautDP.getTimeString());
		Log.i(l, "Deneb - " + denebDP.getTimeString());

		Log.i(l, "Regul - " + regulDP.getTimeString());
		Log.i(l, "Adara - " + adaraDP.getTimeString());
		Log.i(l, "Castor - " + castorDP.getTimeString());

		httpRequestDemo("http://tut.by");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void httpRequestDemo(String spec) {
		URI uri = null;
		try {
			uri = new URI(spec);
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
		Log.d("AM", "Host is " + uri.getHost());

		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest httpUriRequest = new HttpGet(uri);

		Log.d("AM", "Method is " + httpUriRequest.getMethod());

		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpUriRequest);
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		if (httpResponse == null) {
			Log.w("AM", "httpResponse is null");
			return;
		}
		Log.d("AM", "Status line is " + httpResponse.getStatusLine());

		StatusLine statusLine = httpResponse.getStatusLine();
		Log.d("AM", "Reason phase is " + statusLine.getReasonPhrase());

		if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			try {
				httpResponse.getEntity().writeTo(outStream);
				outStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			Log.d("AM", "Size of out stream is " + outStream.size());
			String res = outStream.toString();
			Log.d("AM", "response: " + res);
		} else {
			try {
				httpResponse.getEntity().getContent().close();
			} catch (IllegalStateException ise) {
				ise.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

	}

	private void jsonTest1() {
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl("http://10.24.9.32:8080/TestRestful/get/schoolsByCity");

		try {
			JSONArray cs = json.getJSONArray("schools");
			for (int i = 0; i < cs.length(); i++) {
				JSONObject o = cs.optJSONObject(i);
				String name = o.getString("name");
				Log.d("AM", "id " + name);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
