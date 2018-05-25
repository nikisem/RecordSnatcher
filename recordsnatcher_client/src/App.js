import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {NavBar} from "./NavBar";
import {Main} from "./Main";

class App extends Component {


    render() {

        return (

            <Router>
                <div>
                    <NavBar/>
                    <Switch>
                        <Route exact={true} path='/'
                               render={(props) => <Main {...props}/>}/>
                    </Switch>
                </div>
            </Router>

        );
    }
}

export default App;