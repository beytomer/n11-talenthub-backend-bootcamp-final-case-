import { Stack } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import { FormControl, InputLabel, Input, Button } from '@mui/material';

const DeleteUser = () => {
  const [values, setValues] = useState({});

  const set = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const update = () => {
    axios.patch(`http://localhost:8081/api/v1/users/${values.id}`, values);
  };

  return (
    <Stack spacing={2} width={300}>
      <FormControl>
        <InputLabel htmlFor='id'>Id</InputLabel>
        <Input
          id='id'
          value={values.id}
          onChange={(e) => {
            set({ id: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='name'>Name</InputLabel>
        <Input
          id='name'
          value={values.name}
          onChange={(e) => {
            set({ name: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='surname'>Surname</InputLabel>
        <Input
          id='surname'
          value={values.surname}
          onChange={(e) => {
            set({ surname: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='birthDate'>BirthDate</InputLabel>
        <Input
          id='birthDate'
          value={values.birthDate}
          onChange={(e) => {
            set({ birthDate: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='email'>Email</InputLabel>
        <Input
          id='email'
          value={values.email}
          onChange={(e) => {
            set({ email: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='gender'>Gender</InputLabel>
        <Input
          id='gender'
          value={values.gender}
          onChange={(e) => {
            set({ gender: e.target.value });
          }}
        />
      </FormControl>

      <Button onClick={update}>GUNCELLE</Button>
    </Stack>
  );
};

export default DeleteUser;
