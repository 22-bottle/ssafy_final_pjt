import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

const url = '/place';

function hotPlace(success, fail) {
  server.get(`${url}/hot`).then(success).catch(fail);
}

export { hotPlace };
