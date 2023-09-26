import {Component} from 'react';

class CustomersList extends Component {

    constructor(props) {
        super(props);
        this.state = {clients: []};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch('http://localhost:8080/customers')
            .then(response => response.json())
            .then(data => {
                this.setState({clients: data});
            });
    }
}

export default CustomersList;