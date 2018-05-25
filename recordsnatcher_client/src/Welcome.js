import React from "react";

export class Welcome extends React.Component {


    render() {

        let w;

        if (this.props.welcome === "on") {

            w = <div className="center">
                <br/>
                <div>Welcome to Record Snatcher beta.<br/>
                    Record Snatcher is a power search tool for music collectors. It gets you the latest listings
                    from:
                </div>
                <ul className="welcomeList">
                    <li>eBay.com</li>
                    <li>eBay UK</li>
                    <li>eBay Austria</li>
                    <li>eBay Belgium</li>
                    <li>eBay France</li>
                    <li>eBay Germany</li>
                    <li>eBay Ireland</li>
                    <li>eBay Italy</li>
                    <li>eBay Netherland</li>
                    <li>eBay Poland</li>
                    <li>eBay Spain</li>
                    <li>eBay Switzerland</li>
                    <li>eBay Australia</li>
                    <li>eBay Hong Kong</li>
                    <li>eBay India</li>
                    <li>eBay Malaysia</li>
                    <li>eBay Philippines</li>
                    <li>eBay Singapore</li>
                    <li>eBay Canada</li>
                    <li>huuto.net (Finland)</li>
                </ul>
                <br/>
            </div>

        } else {

            w = ""

        }

        return (

            <div>
                <div>{w}</div>
            </div>

        );
    }
}