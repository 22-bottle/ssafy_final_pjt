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

export { hotTheme, allTheme, allTags, createTheme, themesOfPlace, curTheme };
