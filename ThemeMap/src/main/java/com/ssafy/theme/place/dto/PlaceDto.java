package com.ssafy.theme.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {

	private String placeId;
	private String placeName;
	private String latitude;
	private String longitude;
	private String scoreSum;
	private String scoreCount;
	private String address;
	private String phone;
	
}
