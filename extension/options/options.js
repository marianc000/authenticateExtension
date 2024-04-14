function onChange(e) {
    console.log(">onchange", e.target);
    chrome.storage.local.set({token:e.target.value});
}

document.querySelectorAll('input[type="radio"]')
    .values().forEach(e => e.addEventListener('change', onChange));