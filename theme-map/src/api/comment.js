import { serverAxios } from '@/util/http-commons';

const server = serverAxios();

const url = '/comment';

function registComment(comment, success, fail) {
    console.log(comment);
    server.post(`${url}/regist`, comment).then(success).catch(fail);
}

function commentsOfPlace(placeId, success, fail) {
    console.log(placeId);
    server.get(`${url}/comments/${placeId}`).then(success).catch(fail);
}

export { registComment, commentsOfPlace };