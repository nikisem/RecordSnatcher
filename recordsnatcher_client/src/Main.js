import React from 'react';
import './Main.css';
import {Searching} from "./Searching";
import {addToFilterArray, clearFilterArray, fetchData, filterArray, removeFromFilterArray} from "./Functions";
import generic from './img/generic.png'
import com from './img/com.png'
import uk from './img/uk.png'
import austria from './img/austria.png'
import belgium from './img/belgium.png'
import france from './img/france.png'
import germany from './img/germany.png'
import ireland from './img/ireland.png'
import italy from './img/italy.png'
import netherland from './img/netherland.png'
import poland from './img/poland.png'
import spain from './img/spain.png'
import switzerland from './img/switzerland.png'
import australia from './img/australia.png'
import hongKong from './img/hongKong.png'
import india from './img/india.png'
import malaysia from './img/malaysia.png'
import philippines from './img/philippines.png'
import singapore from './img/singapore.png'
import canada from './img/canada.png'
import huuto from './img/huuto.png'
import up from './img/up.png'
import {Welcome} from "./Welcome";
import {Filters} from "./Filters";
import ScrollToTop from 'react-scroll-up'

export class Main extends React.Component {


    state = {
        keyword: "",
        filterMenu: "off",
        spinner: "off",
        welcome: "on",
        filterKeyword: "",
        filters: filterArray
    }

    handleChange = (event) => {

        this.setState({keyword: event.target.value});

    }


    handleClick = (e) => {

        e.preventDefault()
        this.setState({filterMenu: "off"})
        this.setState({rend: null})
        this.setState({welcome: "off"});
        this.setState({spinner: "on"});
        this.search()
        this.setState({keyword: ""})
        this.setState({filterKeyword: ""})

    }


    enterPressed = (e) => {

        let code = e.keyCode || e.which;

        if (code === 13 && this.state.keyword !== "") {

            this.setState({filterMenu: "off"})
            this.setState({rend: null})
            this.setState({welcome: "off"});
            this.setState({spinner: "on"});
            this.search()
            this.setState({keyword: ""})
            this.setState({filterKeyword: ""})

        }
    }


    async search() {

        this.setState({filterMenu: "off"})
        let keyword = this.state.keyword
        let offset = -(new Date().getTimezoneOffset() / 60)
        let filters = this.state.filters
        const res = await fetchData(keyword, offset, filters);
        await this.setStateAsync({data: res})
        await this.setStateAsync({rend: this.renderItems()})

    }


    setStateAsync(state) {

        clearFilterArray()
        this.setState({spinner: "off"});
        return new Promise((resolve) => {
            this.setState(state, resolve)
        });

    }


    openFilters = () => {

        this.setState({filterMenu: "on"});

    }


    handleFilterChange = (event) => {

        this.setState({filterKeyword: event.target.value});

    }


    handleFilterClick = (e) => {

        e.preventDefault()
        let filterKeyword = this.state.filterKeyword
        addToFilterArray(filterKeyword)
        this.setState({filterKeyword: ""})

    }


    filterEnterPressed = (e) => {

        let code = e.keyCode || e.which;

        if (code === 13 && this.state.keyword === "" && this.state.filterKeyword !== "") {

            let filterKeyword = this.state.filterKeyword
            addToFilterArray(filterKeyword)
            this.setState({filterKeyword: ""})

        }
    }


    removeFilterKeyword = (item) => {

        removeFromFilterArray(item)
        this.setState({update: "yes"});

    }


    preSearch() {

        return (

            <div>
                <Welcome welcome={this.state.welcome}/>
            </div>

        );
    }


    renderItems() {

        let items = this.state.data.map(function (item, index) {
            return (
                <div className="grid-container" key={index}>
                    <div className="grid-item-left">
                        <p>
                            {(() => {
                                switch (item.image) {
                                    case "NO IMAGE AVAILABLE":
                                        return <a target="_blank" href={item.link}><img
                                            className="img-responsive img-thumbnail center-block"
                                            alt="Pic not available" title="Image not available" src={generic}/></a>;

                                    default:
                                        return <a target="_blank" href={item.link}><img
                                            className="img-responsive img-thumbnail center-block" alt={item.title}
                                            src={item.image}/></a>;
                                }
                            })()}
                        </p>
                    </div>
                    <div className="grid-item-right" id="dont-break-out">
                        <p className="timestamp">{item.timeStampString}</p>
                        <p><a target="_blank" href={item.link}>{item.title}</a></p>
                        <p>{item.price}</p>
                        <p>
                            {(() => {
                                switch (item.source) {
                                    case "com":
                                        return <img alt="eBay.com" title="eBay.com" src={com}/>;
                                    case "uk":
                                        return <img alt="UK" title="UK" src={uk}/>;
                                    case "austria":
                                        return <img alt="Austria" title="Austria" src={austria}/>;
                                    case "belgium":
                                        return <img alt="Belgium" title="Belgium" src={belgium}/>;
                                    case "france":
                                        return <img alt="France" title="France" src={france}/>;
                                    case "germany":
                                        return <img alt="Germany" title="Germany" src={germany}/>;
                                    case "ireland":
                                        return <img alt="Ireland" title="Ireland" src={ireland}/>;
                                    case "italy":
                                        return <img alt="Italy" title="Italy" src={italy}/>;
                                    case "netherland":
                                        return <img alt="Netherland" title="Netherland" src={netherland}/>;
                                    case "poland":
                                        return <img alt="Poland" title="Poland" src={poland}/>;
                                    case "spain":
                                        return <img alt="Spain" title="Spain" src={spain}/>;
                                    case "switzerland":
                                        return <img alt="Switzerland" title="Switzerland" src={switzerland}/>;
                                    case "australia":
                                        return <img alt="Australia" title="Australia" src={australia}/>;
                                    case "hong kong":
                                        return <img alt="Hong Kong" title="Hong Kong" src={hongKong}/>;
                                    case "india":
                                        return <img alt="India" title="India" src={india}/>;
                                    case "malaysia":
                                        return <img alt="Malaysia" title="Malaysia" src={malaysia}/>;
                                    case "philippines":
                                        return <img alt="Philippines" title="Philippines" src={philippines}/>;
                                    case "singapore":
                                        return <img alt="Singapore" title="Singapore" src={singapore}/>;
                                    case "canada":
                                        return <img alt="Canada" title="Canada" src={canada}/>;
                                    case "huuto":
                                        return <img alt="Huuto.net" title="Huuto.net" src={huuto}/>;
                                    default:
                                        return;
                                }
                            })()}
                        </p>
                    </div>
                </div>)

        })

        if (items.length === 0) {

            items = <div>
                <br/>
                <div className="center">NO RESULTS FOUND</div>
                <br/>
            </div>
        }

        return (

            <div>
                <div>
                    {items}
                    <ScrollToTop showUnder={160}>
                        <img alt="UP" title="Scroll to top" src={up}/>
                    </ScrollToTop>
                </div>
            </div>

        );
    }

    render() {

        return (

            <div>
                <div className="grid-item-left">
                    <input value={this.state.keyword} onChange={this.handleChange} onKeyPress={this.enterPressed}
                           type="search" name="search"
                           required/>
                    <input disabled={(this.state.keyword.length < 1)} onClick={this.handleClick} type="submit"
                           value="Search"/>
                    <br/>
                    <br/>
                    <Filters filterMenu={this.state.filterMenu} openFilters={this.openFilters}
                             handleFilterChange={this.handleFilterChange} filterEnterPressed={this.filterEnterPressed}
                             handleFilterClick={this.handleFilterClick}
                             filterKeyword={this.state.filterKeyword} removeFilterKeyword={this.removeFilterKeyword}/>
                </div>
                <br/>
                <div>
                    <Searching spinner={this.state.spinner}/>
                </div>

                <div>{this.state.rend || this.preSearch()}</div>
            </div>

        );
    }
}