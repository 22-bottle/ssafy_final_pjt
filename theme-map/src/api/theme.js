import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

const url = '/theme';

function hotTheme(success, fail) {
  server.get(`${url}/hot`).then(success).catch(fail);
}

function allTheme(success, fail) {
  server.get(`${url}/all`).then(success).catch(fail);
}

function allTags(success, fail) {
  server.get(`${url}/allTags`).then(success).catch(fail);
}

function createTheme(theme, success, fail) {
  server.post(`${url}/create`, JSON.stringify(theme)).then(success).catch(fail);
}

function themesOfPlace(placeId, success, fail) {
  console.log(placeId);
  server.get(`${url}/place/${placeId}`).then(success).catch(fail);
}

function curTheme(themeId, success, fail) {
  server.get(`${url}/get/${themeId}`).then(success).catch(fail);
}

function themesOfTag(tags, success, fail) {
  server.post(`${url}/tag`, JSON.stringify(tags)).then(success).catch(fail);
}

function didLike(editorId, themeId, success, fail) {
  server.get(`${url}/didLike/${editorId}/${themeId}`).then(success).catch(fail);
}

function postLike(likeDto, success, fail) {
  server.post(`${url}/postLike`, JSON.stringify(likeDto)).then(success).catch(fail);
}

function disLike(likeDto, success, fail) {
  server.post(`${url}/disLike`, JSON.stringify(likeDto)).then(success).catch(fail);
}

export { hotTheme, allTheme, allTags, createTheme, themesOfPlace, curTheme, themesOfTag, didLike, postLike, disLike };
