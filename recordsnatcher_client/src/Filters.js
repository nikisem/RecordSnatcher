import React from "react";
import './Main.css';
import {filterArray} from "./Functions";
import remove from './img/remove.png'
import add from './img/add.png'

export class Filters extends React.Component {


    render() {

        let filterKeywords = filterArray.map(function (item, index) {
            return (
                <div className="filterKeyword" id="dont-break-out" key={index}>
                    <p>
                        {item} <span onClick={() => this.props.removeFilterKeyword(item)}>   <img alt="x"
                                                                                                  title="Remove filter keyword"
                                                                                                  src={remove}/></span>
                    </p>
                </div>)
        }, this)

        let f;

        if (this.props.filterMenu === "on") {

            f = <div>
                <input value={this.props.filterKeyword} onChange={this.props.handleFilterChange}
                       onKeyPress={this.props.filterEnterPressed}
                       required/>
                <input disabled={(this.props.filterKeyword.length < 1)} onClick={this.props.handleFilterClick}
                       type="submit"
                       value="Add"/>
                <br/>
                <br/>
                <div>{filterKeywords}</div>
            </div>

        } else {

            f = <div className="openFilter" onClick={this.props.openFilters}><img alt="+" title="Add filters"
                                                                                  src={add}/> add filters
                (optional)</div>

        }

        return (

            <div>
                <div>{f}</div>
            </div>

        );
    }
}