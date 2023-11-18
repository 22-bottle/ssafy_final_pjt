import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

const url = '/place';

const kakaoPlace = {
  address_name: '',
  category_group_code: '',
  category_group_name: '',
  category_name: '',
  distance: '',
  id: '',
  phone: '',
  place_name: '',
  place_url: '',
  road_address_name: '',
  x: '',
  y: '',
};
const placeDto = {
  placeId: '',
  placeName: '',
  latitude: '',
  longitude: '',
  scoreSum: '',
  scoreCount: '',
  address: '',
  phone: '',
};

function kakaoToDto(kakao) {
  placeDto.placeId = kakao.id;
  placeDto.placeName = kakao.place_name;
  placeDto.latitude = kakao.y;
  placeDto.longitude = kakao.x;
  placeDto.address = kakao.road_address_name;
  placeDto.phone = kakao.phone;
  return placeDto;
}

function dtoToKakao(dto) {
  kakaoPlace.id = dto.place_id;
  kakaoPlace.phone = dto.phone;
  kakaoPlace.place_name = dto.place_name;
  kakaoPlace.place_url = 'https://place.map.kakao.com/' + dto.place_id;
  kakaoPlace.road_address_name = dto.address;
  kakaoPlace.x = dto.longitude;
  kakaoPlace.y = dto.latitude;
  return kakaoPlace;
}

function hotPlace(success, fail) {
  server.get(`${url}/hot`).then(success).catch(fail);
}

function themePlace(themeId, success, fail) {
  server.get(`${url}/theme/${themeId}`).then(success).catch(fail);
}

function createPlace(placeDto, success, fail) {
  server.post(`${url}/create`, placeDto).then(success).catch(fail);
}

function linkPlace(linkDto, success, fail) {
  server.post(`${url}/link`, linkDto).then(success).catch(fail);
}

export { hotPlace, themePlace, createPlace, linkPlace, kakaoToDto, dtoToKakao };
