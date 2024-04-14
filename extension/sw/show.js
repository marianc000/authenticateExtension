let html = fetch('popup.html').then(r => r.text());

export async function display(tabId, obj) {
    const id = chrome.runtime.id;
    return chrome.scripting.executeScript({
        target: { tabId },
        func: showPopup,
        args: [await html, id, obj]
    })
}

function showPopup(html, id, obj) {
    document.getElementById(id)?.remove();
    html = html.replace('DIV_ID', id).replace('CONTENT', JSON.stringify(obj, null, '  '));

    document.body.insertAdjacentHTML('beforeend', html);

    const containerDiv = document.getElementById(id);
    containerDiv.addEventListener('click', e => {
        if (e.target === containerDiv)
            containerDiv.remove();
    });
}