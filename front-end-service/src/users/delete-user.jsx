import { Stack } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import { FormControl, InputLabel, Input, Button } from '@mui/material';

const Delete = () => {
  const [values, setValues] = useState({});

  const set = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const deleteValue = () => {
    axios.delete(`http://localhost:8081/api/v1/users/${values.id}`);
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

      <Button onClick={deleteValue}>SIL</Button>
    </Stack>
  );
};

export default Delete;
