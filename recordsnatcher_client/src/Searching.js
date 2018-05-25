import React from "react";

export class Searching extends React.Component {


    render() {

        let Spinner = require('react-spinkit');
        let sp;

        if (this.props.spinner === "off") {

            sp = ""

        }

        if (this.props.spinner === "on") {

            sp = <div className="center">
                <br/>
                <Spinner name="ball-pulse-sync"/>
                <br/>
                SEARCHING - This will take several seconds
                <br/>
            </div>

        }

        return (

            <div>
                <div>{sp}</div>
            </div>

        );
    }
}