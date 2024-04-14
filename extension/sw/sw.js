import { setIcon } from './states.js';
import { display } from './show.js';
import { get } from './net.js';

chrome.runtime.onInstalled.addListener(() => {
  chrome.storage.local.set({ token: 'access_token' });
  setIcon();
});

const URL = 'https://chromeextensionsession.oa.r.appspot.com/data';

chrome.storage.onChanged.addListener(o => {
  chrome.cookies.getAll({ url: URL })
    .then(cookies => cookies.forEach(({ name }) => chrome.cookies.remove({ url: URL, name })));
});

chrome.action.onClicked.addListener(async ({ id: tabId }) => {
  await chrome.action.disable(tabId);
  
  const { token } = await chrome.storage.local.get('token');

  let p = get(URL);
  if (token === 'access_token')
    p = p.catch(er => accessToken().then(token => get(URL + '?access_token=' + token)));
  else
    p = p.catch(er => idToken().then(token => get(URL + '?id_token=' + token)));

  await p.then(obj => display(tabId, obj))
    .then(() => setIcon(token)).catch(setIcon);

  await chrome.action.enable(tabId);
});

function accessToken() {
  return chrome.identity.getAuthToken({ interactive: true }).then(({ token }) => token);
}

function idToken() {
  const { client_id_for_web, redirect_path, scopes } = chrome.runtime.getManifest().oauth2;

  const redirectUri = chrome.identity.getRedirectURL(redirect_path);

  const url = 'https://accounts.google.com/o/oauth2/v2/auth?response_type=id_token' +
    '&client_id=' + client_id_for_web + '&redirect_uri=' + redirectUri +
    '&scope=' + scopes.join(' ') + '&nonce=any';

  return chrome.identity.launchWebAuthFlow({ interactive: true, url })
    .then(url => new URLSearchParams(url.split('#')[1]).get('id_token'));
}
