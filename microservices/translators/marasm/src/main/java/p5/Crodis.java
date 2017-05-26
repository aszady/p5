package p5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crodis {
	public static class Item {
		float latitude;
		float longitude;
		float radius;
		Map<String, Float> conditions;

		// Temperature [Celsius]
		public static String CONDITION_TEMPERATURE = "temperature";

		public float getLatitude() {
			return latitude;
		}

		public void setLatitude(float latitude) {
			this.latitude = latitude;
		}

		public float getLongitude() {
			return longitude;
		}

		public void setLongitude(float longitude) {
			this.longitude = longitude;
		}

		public float getRadius() {
			return radius;
		}

		public void setRadius(float radius) {
			this.radius = radius;
		}

		public Map<String, Float> getConditions() {
			return conditions;
		}

		public void setConditions(Map<String, Float> conditions) {
			this.conditions = conditions;
		}
	}

	private String source;
	private List<Item> items;

	public Crodis(String source) {
		this.source = source;
		this.items = new ArrayList<Item>();
	}

	public void addItem(float lat, float lon, float radius, Map<String, Float> conditions) {
		Item item = new Item();
		item.setLatitude(lat);
		item.setLongitude(lon);
		item.setRadius(radius);
		Map<String, Float> clonedConditions = new HashMap<String, Float>();
		clonedConditions.putAll(conditions);
		item.setConditions(clonedConditions);
		this.items.add(item);
	}

	public String getSource() {
		return this.source;
	}
	public List<Item> getItems() { return this.items; }
}
