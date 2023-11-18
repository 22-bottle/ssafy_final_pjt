import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

const url = '/theme';

function hotTheme(success, fail) {
    server.get(`${url}/hot`).then(success).catch(fail);
}

function allTheme(tags, success, fail) {
    server.get(`${url}/tag`, JSON.stringify).then(success).catch(fail);
}

export { hotTheme, allTheme };