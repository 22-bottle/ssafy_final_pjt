import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

async function editorConfirm(param, success, fail) {
  console.log('param', param);
  await server.post(`/editor/login`, param).then(success).catch(fail);
  console.log('editorConfirm ok');
}

async function findById(id, success, fail) {
  server.defaults.headers['Authorization'] = sessionStorage.getItem('accessToken');
  await server.get(`/editor/info/${id}`).then(success).catch(fail);
}

async function tokenRegeneration(editor, success, fail) {
  server.defaults.headers['refreshToken'] = sessionStorage.getItem('refreshToken');
  await server.post(`/editor/refresh`, editor).then(success).catch(fail);
}

async function regist(param, success, fail) {
  await server.post(`/editor/regist`, param).then(success).catch(fail);
}

async function logout(id, success, fail) {
  await server.get(`/editor/logout/${id}`).then(success).catch(fail);
}

async function modify(param, success, fail) {
  await server.patch(`/editor/modify`, param).then(success).catch(fail);
}

async function resign(id, success, fail) {
  await server.delete(`/editor/resign`, { data: id }).then(success).catch(fail);
}

export { editorConfirm, findById, tokenRegeneration, regist, logout, modify, resign };
