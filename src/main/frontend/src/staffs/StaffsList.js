import {Component} from 'react';

class StaffsList extends Component {

    constructor(props) {
        super(props);
        this.state = {clients: []};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch('http://localhost:8080/staffs')
            .then(response => response.json())
            .then(data => {
                this.setState({clients: data});
            });
    }
}

export default StaffsList;