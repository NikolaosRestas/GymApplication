import './App.css';
import {Component} from "react";
import { Switch, Route } from 'react-router-dom';
import Navbar from "./navbar/Navbar";
import CountiesPage from "./counties/counties";
import GymsPage from "./gyms/gyms";
import CustomersPage from "./customers/customers";
import ProgramsPage from "./programs/programs";
import StaffsPage from "./staffs/staffs";

class App extends Component {

    render() {
        return (
            <div>
                <Navbar/>
                <Switch>
                    <Route path="/counties" component={CountiesPage} />
                    <Route path="/gyms" component={GymsPage} />
                    <Route path="/customers" component={CustomersPage} />
                    <Route path="/programs" component={ProgramsPage} />
                    <Route path="/staffs" component={StaffsPage} />
                </Switch>

            </div>
        );
    }
}

export default App;
