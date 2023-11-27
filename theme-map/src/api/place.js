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
  return {
    id: dto.placeId,
    phone: dto.phone,
    place_name: dto.placeName,
    place_url: 'https://place.map.kakao.com/' + dto.placeId,
    road_address_name: dto.address,
    x: dto.longitude,
    y: dto.latitude,
    address_name: 'a',
    category_group_code: dto.placeId,
    category_group_name: 'a',
    category_name: 'a',
    distance: Number(dto.scoreCount) == 0 ? 0 : (Number(dto.scoreSum) / Number(dto.scoreCount)).toFixed(1),
  };
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

function keepScore(scoreDto, success, fail) {
  server.put(`${url}/score`, JSON.stringify(scoreDto)).then(success).catch(fail);
}

function isThere(placeId, success, fail) {
  server.get(`${url}/isThere/${placeId}`).then(success).catch(fail);
}

function isInTheme(themeId, placeId, success, fail) {
  server.get(`${url}/isInTheme/${themeId}/${placeId}`).then(success).catch(fail);
}

function whoCreated(themeId, placeId, success, fail) {
  server.get(`${url}/who/${themeId}/${placeId}`).then(success).catch(fail);
}

function deletePlace(themeId, placeId, success, fail) {
  server.delete(`${url}/delete/${themeId}/${placeId}`).then(success).catch(fail);
}

function getSpareNum(themeId, editorId, success, fail) {
  server.get(`${url}/spare/${themeId}/${editorId}`).then(success).catch(fail);
}

export { hotPlace, themePlace, createPlace, linkPlace, kakaoToDto, dtoToKakao, keepScore, isThere, isInTheme, whoCreated, getSpareNum, deletePlace };
