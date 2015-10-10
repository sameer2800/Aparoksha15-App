package com.aparoksha.main;

import java.util.ArrayList;
import java.util.List;

import com.aparoksha.main.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CardArrayAdapter extends ArrayAdapter<Card> {

	private static final String	TAG			= "CardArrayAdapter";
	private List<Card>			cardList	= new ArrayList<Card>();

	static class CardViewHolder {

		TextView	eventName;
		ImageView	eventImage;
	}

	public CardArrayAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(Card object) {
		cardList.add(object);
		super.add(object);
	}

	@Override
	public int getCount() {
		return this.cardList.size();
	}

	@Override
	public Card getItem(int index) {
		return this.cardList.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CardViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.list_item_card, parent, false);
			viewHolder = new CardViewHolder();
			viewHolder.eventName = (TextView) row
					.findViewById(R.id.card_textView);
			viewHolder.eventImage = (ImageView) row
					.findViewById(R.id.card_imageView);
			row.setTag(viewHolder);
		} else {
			viewHolder = (CardViewHolder) row.getTag();
		}
		Card card = getItem(position);
		viewHolder.eventName.setText(card.getEventName());
		viewHolder.eventImage.setImageResource(card.getEventImage());
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}
