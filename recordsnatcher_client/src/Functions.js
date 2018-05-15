export function fetchData(keyword, offset) {
    return fetch("http://www.apiurl.com/api/get?keyword=" + keyword + "&offset=" + offset).then(function (response) {

        return response.json();

    })

}
