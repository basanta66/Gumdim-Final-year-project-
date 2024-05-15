import React from 'react'
import Hero from '../Components/Hero/Hero'
import Popular from '../Components/Popular/Popular'
import NewDestination from '../Components/NewDestination/NewDestination'
import { useParams } from 'react-router-dom'

const Home = () => {
  // const { userId } = useParams();
  return (
    <div>
      <Hero />
      <Popular />
      <NewDestination />
    </div>
  )
}

export default Home
