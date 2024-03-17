import { Stack } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import { FormControl, InputLabel, Input, Button } from '@mui/material';

const AddAddress = () => {
  const [values, setValues] = useState({});

  const set = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const add = () => {
    axios.post('http://localhost:8081/api/v1/addresses', values);
  };

  return (
    <Stack spacing={2} width={300}>
      <FormControl>
        <InputLabel htmlFor='city'>City</InputLabel>
        <Input
          id='city'
          value={values.city}
          onChange={(e) => {
            set({ city: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='country'>Country</InputLabel>
        <Input
          id='county'
          value={values.county}
          onChange={(e) => {
            set({ county: e.target.value });
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
      <FormControl>
        <InputLabel htmlFor='userId'>UserId</InputLabel>
        <Input
          id='userId'
          value={values.userId}
          onChange={(e) => {
            set({ userId: e.target.value });
          }}
        />
      </FormControl>

      <Button onClick={add}>Ekle</Button>
    </Stack>
  );
};

export default AddAddress;
