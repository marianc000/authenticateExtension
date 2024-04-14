const IMG_DIR = '/imgs/';

export function setIcon(name='off') {
    chrome.action.setIcon({ path: IMG_DIR + name + '.png' });
}
