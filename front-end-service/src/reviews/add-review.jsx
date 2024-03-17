import { Stack } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import { FormControl, InputLabel, Input, Button } from '@mui/material';

const AddReview = () => {
  const [values, setValues] = useState({});

  const set = (value) => {
    setValues((prev) => {
      return { ...prev, ...value };
    });
  };

  const add = () => {
    axios.post('http://localhost:8081/api/v1/reviews', values);
  };

  return (
    <Stack spacing={2} width={300}>
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
      <FormControl>
        <InputLabel htmlFor='restaurantId'>RestaurantId</InputLabel>
        <Input
          id='restaurantId'
          value={values.restaurantId}
          onChange={(e) => {
            set({ restaurantId: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='rate'>Rate</InputLabel>
        <Input
          id='rate'
          value={values.rate}
          onChange={(e) => {
            set({ rate: e.target.value });
          }}
        />
      </FormControl>
      <FormControl>
        <InputLabel htmlFor='comment'>Comment</InputLabel>
        <Input
          id='comment'
          value={values.comment}
          onChange={(e) => {
            set({ comment: e.target.value });
          }}
        />
      </FormControl>

      <Button onClick={add}>Ekle</Button>
    </Stack>
  );
};

export default AddReview;
