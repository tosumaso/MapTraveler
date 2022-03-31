
// ページの一番腕までスクロール
const scroll_to_top = document.querySelector('#scroll-to-top');

scroll_to_top.addEventListener('click', () => {
  window.scroll({top:0,behavior: 'smooth'});
});