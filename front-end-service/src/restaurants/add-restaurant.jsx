import { Stack } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import { FormControl, InputLabel, Input, Button } from '@mui/material';

const AddRestaurant = () => {
  const [values, setValues] = useState({});

  const set = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const add = () => {
    axios.post('http://localhost:8080/api/v1/restaurants', values);
  };

  return (
    <Stack spacing={2} width={300}>
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
        <InputLabel htmlFor='location'>Location</InputLabel>
        <Input
          id='location'
          value={values.location}
          onChange={(e) => {
            set({ location: e.target.value });
          }}
        />
      </FormControl>

      <Button onClick={add}>Ekle</Button>
    </Stack>
  );
};

export default AddRestaurant;
