import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import {
  Alert,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import { useState } from "react";
import EditProgramModal from "./EditProgramModal";
import VisibilityIcon from "@mui/icons-material/Visibility";
import IconButton from "@mui/material/IconButton";

export default function ProgramsTableComponent({ programs, onChange }) {
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedProgram, setSelectedProgram] = useState(null);
  const [isSuccessfulDelete, setIsSuccessfulDelete] = useState(false);
  const [isCustomerModalOpen, setCustomerModalOpen] = useState(false);

  function onEdit(program) {
    console.log("Edit program: ", program);
    setSelectedProgram(program);
    setIsEditModalOpen(true);
  }

  function onDelete(program) {
    console.log("I am going to delete program: ", program);
    fetch(`/programs/${program.id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((response) => {
      if (response.ok) {
        setIsSuccessfulDelete(true);
        setTimeout(() => {
          setIsSuccessfulDelete(false);
        }, 5000);
        onChange(programs.filter((c) => c.id !== program.id));
      }
    });
  }

  const handleCloseEditModal = () => {
    setIsEditModalOpen(false);
  };

  const handleCustomerModalOpen = () => {
    setCustomerModalOpen(true);
  };

  const handleCustomerModalClose = () => {
    setCustomerModalOpen(false);
  };

  return (
    <React.Fragment>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Id</TableCell>
              <TableCell align="right">Kind</TableCell>
              <TableCell align="right">Duration</TableCell>
              <TableCell align="right">Price</TableCell>
              <TableCell align="right">Customer</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {programs.map((program) => (
              <TableRow
                key={program.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {program.id}
                </TableCell>
                <TableCell align="right">{program.kind}</TableCell>
                <TableCell align="right">{program.duration}</TableCell>
                <TableCell align="right">{program.price}</TableCell>

                <TableCell align="right">
                  <IconButton onClick={handleCustomerModalOpen}>
                    <VisibilityIcon></VisibilityIcon>
                  </IconButton>
                  <Dialog open={isCustomerModalOpen}>
                    <DialogContent>
                      <TableHead>
                        <TableRow>
                          <TableCell>Id</TableCell>
                          <TableCell align="right">Name</TableCell>
                          <TableCell align="right">Address</TableCell>
                          <TableCell align="right">Email</TableCell>
                          <TableCell align="right">Phone</TableCell>
                          <TableCell align="right">Gym</TableCell>
                        </TableRow>
                      </TableHead>
                      {program.customers.map((customer) => (
                        <TableRow
                          key={customer.id}
                          sx={{
                            "&:last-child td, &:last-child th": { border: 0 },
                          }}
                        >
                          <TableCell component="th" scope="row">
                            {customer.id}
                          </TableCell>
                          <TableCell align="right">{customer.name}</TableCell>
                          <TableCell align="right">
                            {customer.address}
                          </TableCell>
                          <TableCell align="right">{customer.email}</TableCell>
                          <TableCell align="right">{customer.phone}</TableCell>
                          <TableCell align="right">
                            {customer.gym.name}
                          </TableCell>
                        </TableRow>
                      ))}
                    </DialogContent>
                    <DialogActions>
                      <Button onClick={handleCustomerModalClose}>Close</Button>
                    </DialogActions>
                  </Dialog>
                </TableCell>

                <TableCell align="right">
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => onEdit(program)}
                  >
                    Edit
                  </Button>
                  <span className="inline-block w-4"></span>{" "}
                  {/* This creates space */}
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => onDelete(program, programs)}
                  >
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {selectedProgram && (
        <EditProgramModal
          isOpen={isEditModalOpen}
          onClose={handleCloseEditModal}
          clientData={selectedProgram}
          onSave={onChange}
        />
      )}

      <div className="relative h-32 flex flex-nowrap">
        <div className="absolute inset-x-0 bottom-0 h-16 flex flex-nowrap">
          {isSuccessfulDelete === true && (
            <Alert severity="success">The program deleted successful!</Alert>
          )}
        </div>
      </div>
    </React.Fragment>
  );
}
