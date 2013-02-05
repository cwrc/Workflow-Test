package cwrc.notification.util;

import java.util.ArrayList;

import cwrc.notification.logic.Response;

public class Convertor {
	public static Response[] Arraylist2Array(ArrayList<Response> input){
		Response[] output = new Response[input.size()];
		for (int i = 0; i < output.length; i++) {
			output[i] = input.get(i);
		}
		return output;
	}
}
