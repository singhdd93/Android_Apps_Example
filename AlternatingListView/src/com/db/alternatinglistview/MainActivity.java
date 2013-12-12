package com.db.alternatinglistview;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

public class MainActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create our own version of the list adapter
		List<Car> cars = getData();
		ListAdapter adapter = new CarListAdapter(this, cars, android.R.layout.simple_list_item_2, new String[] {
				Car.KEY_MODEL, Car.KEY_MAKE }, new int[] { android.R.id.text1, android.R.id.text2 });
		this.setListAdapter(adapter);
	}

	private List<Car> getData() {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car("Dodge", "Viper"));
		cars.add(new Car("Chevrolet", "Corvette"));
		cars.add(new Car("Aston Martin", "Vanquish"));
		cars.add(new Car("Lamborghini", "Diablo"));
		cars.add(new Car("Ford", "Pinto"));
		return cars;
	}
}