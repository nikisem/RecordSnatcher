import React from 'react';
import './Main.css';
import {fetchData} from "./Functions";
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


export class Main extends React.Component {

    state = {
        keyword: "",
        spinner: ""
    }

    handleClick = (e) => {
        e.preventDefault()
        this.search()
        this.spin()
    }

    enterPressed = (e) => {
        var code = e.keyCode || e.which;
        if (code === 13 && this.state.keyword !== "") {
            this.search()
            this.spin()
        }
    }


    async search() {

        console.log("SEARCHING FOR ", this.state.keyword)

        console.log("FETCHING DATA")

        let keyword = this.state.keyword

        let offset = -(new Date().getTimezoneOffset() / 60)

        const res = await fetchData(keyword, offset);

        await this.setStateAsync({data: res})

        await this.setStateAsync({rend: this.renderItems()})

        console.log("DONE")

    }

    setStateAsync(state) {
        return new Promise((resolve) => {
            this.setState(state, resolve)
        });
    }

    handleChange = (event) => {
        this.setState({keyword: event.target.value});
    }

    spin = () => {
        var Spinner = require('react-spinkit');
        var sp = <div><Spinner name="ball-pulse-sync"/> <br/>SEARCHING - This will take several seconds</div>

        this.setState({spinner: sp});

    }


    searching() {
        return (
            <div>

                <div className="grid-item-left">
                    <input onChange={this.handleChange} onKeyPress={this.enterPressed} type="search" name="search"
                           required/>
                    <input disabled={(this.state.keyword.length < 1)} onClick={this.handleClick} type="submit"
                           value="Search"/>
                </div>

                <div className="grid-item-left" id="load">
                    <div>Welcome to Record Snatcher beta.<br/>
                        Record Snatcher is a power search tool for music collectors. It gets you the latest listings
                        from 19 diffirent eBay sites and huuto.net
                    </div>
                    <br/>
                    <br/>
                    <br/>
                    {this.state.spinner}
                </div>


            </div>
        );
    }


    renderItems() {
        this.setState({spinner: ""});
        var items = this.state.data.map(function (item, index) {
            return (
                <div className="grid-container" key={index}>
                    <div className="grid-item-left">
                        <p>
                            {(() => {
                                switch (item.image) {
                                    case "NO IMAGE AVAILABLE":
                                        return <a target="_blank" href={item.link}><img
                                            className="img-responsive img-thumbnail center-block"
                                            alt="Image not available" title="Image not available" src={generic}/></a>;

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
                        <p className="title"><a target="_blank" href={item.link}>{item.title}</a></p>
                        <p className="price">{item.price}</p>

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
        return (
            <div>
                <div>

                    <div className="grid-item-left">
                        <input onChange={this.handleChange} type="search" name="search" required/>
                        <input disabled={(this.state.keyword.length < 1)} onClick={this.handleClick}
                               onKeyPress={this.enterPressed} type="submit"
                               value="Search"/>
                    </div>
                    <div className="grid-item-left" id="load">{this.state.spinner}</div>


                </div>

                <div>
                    {items}
                </div>
            </div>
        );
    }

    render() {
        return (
            <div>

                <div>{this.state.rend || this.searching()}</div>

            </div>
        );

    }

}
