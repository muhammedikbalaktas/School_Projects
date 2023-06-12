import math
import sys

# Calculate the Euclidean distance between two cities

def euclidean_distance(city1, city2):
    x1, y1 = city1
    x2, y2 = city2
    distance = math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2)
    return round(distance)


# Find the nearest unvisited city from a given city

def nearest_neighbor(city, unvisited_cities):
    min_distance = math.inf
    nearest_city = None
    for neighbor in unvisited_cities:
        distance = euclidean_distance(city, neighbor)
        if distance < min_distance:
            min_distance = distance
            nearest_city = neighbor
    return nearest_city


# Solve the Half TSP problem using the Nearest Neighbor algorithm

def solve_half_tsp(cities):
    n = len(cities)
    half_n = n // 2

    # Calculate distances between cities
    distances = [[0] * n for _ in range(n)]
    for i in range(n):
        for j in range(n):
            distances[i][j] = euclidean_distance(cities[i], cities[j])

    # Select a random starting city
    start_city = cities[0]
    unvisited_cities = cities[1:]

    # Construct the tour using the Nearest Neighbor algorithm
    tour = [start_city]
    current_city = start_city
    for _ in range(half_n - 1):
        next_city = nearest_neighbor(current_city, unvisited_cities)
        tour.append(next_city)
        unvisited_cities.remove(next_city)
        current_city = next_city

    # Add the starting city to complete the tour
    tour.append(start_city)

    # Calculate the tour length
    tour_length = sum(distances[i][i + 1] for i in range(half_n))

    # Output the tour length and the tour itself
    output = [str(tour_length)] + [str(city) for city in tour]
    return output



def read_input_file(filename):
    cities = []
    try:
        with open(filename, 'r') as file:
            for line in file:
                if line.strip():
                    city_id, x, y = line.split()
                    cities.append((float(x), float(y)))
    except FileNotFoundError:
        print("Input file not found.")
    except ValueError:
        print("Invalid input file format.")
    return cities

def write_output_file(filename, output):
    with open(filename, 'w') as file:
        file.write('\n'.join(output))

# Read input from the file

input_file = 'input-3'
txt ='.txt'
fulname=input_file+txt
cities = read_input_file(fulname)

if cities:
    # Solve the Half TSP problem
    output = solve_half_tsp(cities)

    # Write the output to a file test-output-1.txt
    output_file = 'test-'+input_file+'-output.txt'
    write_output_file(output_file, output)
