import React from 'react';
import logo from './img/logo.jpg'

export class NavBar extends React.Component {


    render() {

        return (

            <div>
                <img className="img-responsive" alt="Record Snatcher" src={logo}/>
            </div>

        );
    }
}