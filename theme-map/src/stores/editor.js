import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import { jwtDecode } from 'jwt-decode';

import { editorConfirm, findById, tokenRegeneration, logout, modify, resign } from '@/api/editor';
import { httpStatusCode } from '@/util/http-status';

export const useEditorStore = defineStore(
  'editorStore',
  () => {
    const router = useRouter();

    const isLogin = ref(false);
    const isLoginError = ref(false);
    const editorInfo = ref({
      editorId: '0',
      id: '0',
      pw: '0',
      salt: '0',
      emailId: '0',
      emailDomain: '0',
      editorName: '0',
      likeSum: '0',
      joinDate: '',
      token: '0',
    });
    const editorDto = ref({
      editorId: '0',
      id: '0',
      pw: '0',
      salt: '0',
      emailId: '0',
      emailDomain: '0',
      editorName: '0',
      likeSum: '0',
      joinDate: '',
      token: '0',
    });
    const sEditorDto = ref({
      editorId: '',
      id: '',
      pw: '',
      salt: '',
      emailId: '',
      emailDomain: '',
      editorName: '',
      likeSum: '',
      joinDate: '',
      token: false,
    });
    const isValidToken = ref(false);

    const cEditorInfo = computed(() => editorInfo);
    const cEditorDto = computed(() => editorDto);
    const cCurEditorDto = computed(() => sEditorDto);
    const cIsLogin = computed(() => isLogin);

    const editorLogin = async (loginEditor) => {
      await editorConfirm(
        loginEditor,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            isLogin.value = true;
            isLoginError.value = false;
            editorInfo.value = response.data;
            editorDto.value = response.data.editor;

            if (loginEditor.setToken) {
              isValidToken.value = true;
              sEditorDto.value = response.data.editor;
              sEditorDto.value.token = true;

              let { data } = response;
              let accessToken = data['access-token'];
              let refreshToken = data['refresh-token'];
              console.log('accessToken', accessToken);
              console.log('refreshToken', refreshToken);
              sessionStorage.setItem('accessToken', accessToken);
              sessionStorage.setItem('refreshToken', refreshToken);

              console.log('sessiontStorage에 담았다', isLogin.value);
            } else {
              sEditorDto.value.token = false;
            }
          } else {
            console.log('로그인 실패했다');
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
          }
        },
        (error) => {
          console.error(error);
        }
      );
    };

    const getEditorInfo = async (token) => {
      let decodeToken = jwtDecode(token);
      console.log('2. decodeToken', decodeToken);
      await findById(
        decodeToken.id,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            editorInfo.value = response.data.editorInfo;
            console.log('3. getEditorInfo data >> ', response.data);
          } else {
            console.log('유저 정보 없음!!!!');
          }
        },
        async (error) => {
          console.error('getEditorInfo() error code [토큰 만료되어 사용 불가능.] ::: ', error.response.status);
          isValidToken.value = false;

          await tokenRegenerate();
        }
      );
    };

    const tokenRegenerate = async () => {
      console.log('토큰 재발급 >> 기존 토큰 정보 : {}', sessionStorage.getItem('accessToken'));
      await tokenRegeneration(
        JSON.stringify(editorInfo.value),
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data['access-token'];
            console.log('재발급 완료 >> 새로운 토큰 : {}', accessToken);
            sessionStorage.setItem('accessToken', accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            console.log('갱신 실패');
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              editorInfo.value.id,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log('리프레시 토큰 제거 성공');
                } else {
                  console.log('리프레시 토큰 제거 실패');
                }
                alert('RefreshToken 기간 만료!!! 다시 로그인해 주세요.');
                isLogin.value = false;
                editorInfo.value = null;
                isValidToken.value = false;
                router.push({ name: 'editor-login' });
              },
              (error) => {
                console.error(error);
                isLogin.value = false;
                editorInfo.value = null;
              }
            );
          }
        }
      );
    };

    const editorLogout = async (id) => {
      await logout(
        id,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false;
            editorInfo.value = null;

            isValidToken.value = false;
            console.log('로그아웃 완료!!');
          } else {
            console.error('에디터 정보가 없습니다!!');
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const editorModify = async (param) => {
      console.log(param);
      await modify(
        param,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            console.log('회원정보 수정 완료!!');
            editorDto.value.id = param.id;
            editorDto.value.emailId = param.emailId;
            editorDto.value.emailDomain = param.emailDomain;
            editorDto.value.editorName = param.editorName;
          } else {
            console.error('회원정보 수정 실패..');
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const editorResign = async (id) => {
      await resign(
        id,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false;
            editorInfo.value = null;
            isValidToken.value = false;
            console.log('탈퇴 성공!!');
          } else {
            console.error('탈퇴 실패..');
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    return {
      isLogin,
      isLoginError,
      editorInfo,
      editorDto,
      sEditorDto,
      isValidToken,
      cEditorInfo,
      cEditorDto,
      cCurEditorDto,
      cIsLogin,
      editorLogin,
      getEditorInfo,
      tokenRegenerate,
      editorLogout,
      editorModify,
      editorResign,
    };
  },
  { persist: { storage: sessionStorage } }
);
