package ca.ualberta.cs.lonelytwitter;

import java.util.Date;
import java.util.List;

import ca.ualberta.cs.lonelytweet.LonelyTweet;
import ca.ualberta.cs.lonelytweet.NormalLonelyTweet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LonelyTwitterActivity extends Activity {

	private static final String SPECIAL_CHARACTER = "*";
	private EditText bodyText;
	private ListView oldTweetsList;

	private List<LonelyTweet> tweets;
	private ArrayAdapter<LonelyTweet> adapter;
	private TweetsFileManager tweetsProvider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
	}

	@Override
	protected void onStart() {
		super.onStart();

		tweetsProvider = new TweetsFileManager(this);
		tweets = tweetsProvider.loadTweets();
		adapter = new ArrayAdapter<LonelyTweet>(this, R.layout.list_item,
				tweets);
		oldTweetsList.setAdapter(adapter);
	}

	public void save(View v) {
//		String text = bodyText.getText().toString();

//		NormalLonelyTweet tweet;

		//tweet = new NormalLonelyTweet(text);

		determineImportance();
	}

	private void determineImportance()
	{

		String text = bodyText.getText().toString();
//
		ImportantLonelyTweet tweet;
		NormalLonelyTweet tweet2;
//
		if (text.contains(SPECIAL_CHARACTER)) {
			
			tweet = new ImportantLonelyTweet(text);
		} else {
			tweet2 = new NormalLonelyTweet(text);
		}
		
		if (tweet.isValid()) {
			tweets.add(tweet);
			adapter.notifyDataSetChanged();

			bodyText.setText("");
			tweetsProvider.saveTweets(tweets);
		} else {
			Toast.makeText(this, "Invalid tweet", Toast.LENGTH_SHORT).show();
		}
	}

	public void clear(View v) {
		tweets.clear();
		adapter.notifyDataSetChanged();
		tweetsProvider.saveTweets(tweets);
	}

}