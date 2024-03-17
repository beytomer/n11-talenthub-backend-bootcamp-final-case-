import { Stack, Button } from '@mui/material';
import { useState } from 'react';
import UsersTable from './users/users-table';
import AddressTable from './addresses/address-table';

import RestaurantsTable from './restaurants/restaurants-table';
import ReviewTable from './reviews/review-table';

function App() {
  const [menu, setMenu] = useState('user');

  return (
    <Stack>
      <Stack direction={'row'}>
        <Button
          onClick={() => {
            setMenu('user');
          }}
        >
          KULLANICILAR
        </Button>
        <Button
          onClick={() => {
            setMenu('restaurant');
          }}
        >
          RESTORANLAR
        </Button>
        <Button
          onClick={() => {
            setMenu('address');
          }}
        >
          ADRESLER
        </Button>
        <Button
          onClick={() => {
            setMenu('review');
          }}
        >
          INCELEMELER
        </Button>
      </Stack>

      <Stack>
        {menu === 'user' && <UsersTable></UsersTable>}
        {menu === 'address' && <AddressTable></AddressTable>}
        {menu === 'restaurant' && <RestaurantsTable></RestaurantsTable>}
        {menu === 'review' && <ReviewTable></ReviewTable>}
      </Stack>
    </Stack>
  );
}

export default App;
