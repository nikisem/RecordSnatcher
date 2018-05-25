export function fetchData(keyword, offset, filters) {
    return fetch("http://www.apiurl.com/api/get?keyword=" + keyword + "&offset=" + offset + "&filters=" + filters).then(function (response) {

        return response.json();

    })
}


export let filterArray = []


export function addToFilterArray(filterKeyword) {

    filterArray.push(filterKeyword);

}


export function removeFromFilterArray(filterKeyword) {

    const index = filterArray.indexOf(filterKeyword);

    if (index !== -1) {

        filterArray.splice(index, 1);

    }
}


export function clearFilterArray() {

    filterArray.splice(0, filterArray.length)

}