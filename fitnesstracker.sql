-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2025 at 01:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fitnesstracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

CREATE TABLE `contact` (
  `contact_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `contact`
--

INSERT INTO `contact` (`contact_id`, `username`, `email`, `user_id`) VALUES
(1, 'cawewa', 'gesan@mailinator.com', -1),
(2, 'abhijit', 'a@gmail.com', -1),
(3, 'abhijtr', 'abhijit@gmial.com', -1),
(4, 'samik', 's@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `exercise`
--

CREATE TABLE `exercise` (
  `exercise_id` int(11) NOT NULL,
  `exercise_name` varchar(100) NOT NULL,
  `exercise_type` varchar(100) NOT NULL,
  `equipment_type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `exercise_workout`
--

CREATE TABLE `exercise_workout` (
  `exercise_id` int(11) NOT NULL,
  `workout_id` int(11) NOT NULL,
  `reps` int(11) NOT NULL,
  `sets` int(11) NOT NULL,
  `weight_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `favorite_meals`
--

CREATE TABLE `favorite_meals` (
  `favorite_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `meal_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `goals`
--

CREATE TABLE `goals` (
  `goal_id` int(11) NOT NULL,
  `goal_name` varchar(100) NOT NULL,
  `target_wt` int(11) NOT NULL,
  `target_cal` int(11) NOT NULL,
  `target_activity` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `meal`
--

CREATE TABLE `meal` (
  `meal_id` int(11) NOT NULL,
  `meal_name` varchar(255) DEFAULT NULL,
  `meal_log_date` date DEFAULT NULL,
  `calories_consumed` int(11) DEFAULT NULL,
  `protein_gm` int(11) DEFAULT NULL,
  `carbs_gm` int(11) DEFAULT NULL,
  `fats_gm` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `meal_type` varchar(50) DEFAULT 'Lunch'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meal`
--

INSERT INTO `meal` (`meal_id`, `meal_name`, `meal_log_date`, `calories_consumed`, `protein_gm`, `carbs_gm`, `fats_gm`, `user_id`, `meal_type`) VALUES
(21, 'asdsad', '2025-05-21', 110, 10, 20, 30, 26, 'Fats');

-- --------------------------------------------------------

--
-- Table structure for table `progress`
--

CREATE TABLE `progress` (
  `progress_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `progress_type` varchar(50) DEFAULT NULL,
  `progress_notes` text DEFAULT NULL,
  `progress_log` date DEFAULT NULL,
  `before_wt` double DEFAULT NULL,
  `after_wt` double DEFAULT NULL,
  `goal_weight` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `progress`
--

INSERT INTO `progress` (`progress_id`, `user_id`, `progress_type`, `progress_notes`, `progress_log`, `before_wt`, `after_wt`, `goal_weight`) VALUES
(107, 26, 'Push Workout', 'Completed benchPress', '2025-05-21', NULL, NULL, NULL),
(108, 26, 'Push Workout', 'Completed inclineDbPress', '2025-05-21', NULL, NULL, NULL),
(109, 26, 'Push Workout', 'Completed machineFly', '2025-05-21', NULL, NULL, NULL),
(110, 26, 'Push Workout', 'Completed tricepsPushdown', '2025-05-21', NULL, NULL, NULL),
(111, 26, 'Push Workout', 'Completed skullCrushers', '2025-05-21', NULL, NULL, NULL),
(112, 26, 'Push Workout', 'Completed latRaises', '2025-05-21', NULL, NULL, NULL),
(113, 26, 'Push Workout', 'Completed dbShoulderPress', '2025-05-21', NULL, NULL, NULL),
(114, 26, 'Weight Update', NULL, '2025-05-21', NULL, 123, NULL),
(115, 26, 'Weight Update', NULL, '2025-05-21', NULL, 123, NULL),
(116, 26, 'Weight Update', NULL, '2025-05-21', NULL, 32, NULL),
(117, 26, 'Goal Weight Update', NULL, '2025-05-21', NULL, NULL, 12312),
(118, 26, 'Weight Update', NULL, '2025-05-21', NULL, 123, NULL),
(119, 26, 'Weight Update', NULL, '2025-05-21', NULL, 123, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `progress_log`
--

CREATE TABLE `progress_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `workout_id` int(11) NOT NULL,
  `exercise_id` int(11) NOT NULL,
  `log_date` date NOT NULL,
  `is_completed` tinyint(1) DEFAULT 0,
  `save_timestamp` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `user_id` int(11) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`user_id`, `role`) VALUES
(26, 'Admin'),
(26, 'Admin'),
(38, 'Admin'),
(38, 'Admin'),
(39, 'Admin'),
(39, 'Admin'),
(40, 'Admin'),
(40, 'Admin'),
(41, 'Admin\r\n'),
(42, 'Admin'),
(41, 'Admin\r\n'),
(42, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `uploadedmeals`
--

CREATE TABLE `uploadedmeals` (
  `uploadedmeals_id` int(11) NOT NULL,
  `uploadedmeals_name` varchar(100) NOT NULL,
  `uploadedmeal_type` varchar(100) NOT NULL,
  `calories` int(10) NOT NULL,
  `macros` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `uploadedmeals`
--

INSERT INTO `uploadedmeals` (`uploadedmeals_id`, `uploadedmeals_name`, `uploadedmeal_type`, `calories`, `macros`) VALUES
(2, 'Turkey Sandwich', 'Lunch/Bulk', 1500, 'Protein: 35gm\r\nCarbs: 175gm\r\nFat: 10gm'),
(3, 'Salmon and Risotto', 'Dinner/Cut', 850, 'Protein:40gm\r\nCarbs:200gm\r\nFat:23gm'),
(4, 'Daal-Bhaat, Massu', 'Lunch/Bulk', 1500, 'Protein: 85gm\r\nCarbs: 350gm\r\nFat: 32gm'),
(5, 'Lasagna', 'Lunch', 1850, 'Protein:160gm\r\nCarbs:500gm\r\nFats:71gm'),
(7, 'Oats', 'Breakfast/Pre-Workout', 500, 'Carbs:200gm\r\nProtein: 9gm\r\nFats: 1gm'),
(9, 'Green Salad', 'Lunch', 750, 'Protein:10gm\r\nCarbs: 20gm\r\nFat:0.1gm'),
(10, 'Garlic Noodles', 'Lunch', 850, 'Protein:20gm\r\nCarbs:150gm\r\nFats:50gm\r\n'),
(26, 'Pasta and Meatbalss', 'Lunch', 1500, 'Protein: 55gm\r\nCarbs: 500gm\r\nFats: 35gm');

-- --------------------------------------------------------

--
-- Table structure for table `uploadedworkout`
--

CREATE TABLE `uploadedworkout` (
  `uploadedworkout_id` int(11) NOT NULL,
  `uploadedworkout_name` varchar(100) NOT NULL,
  `uploadedworkout_type` varchar(50) NOT NULL,
  `uploadedworkout_duration` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `uploadedworkout`
--

INSERT INTO `uploadedworkout` (`uploadedworkout_id`, `uploadedworkout_name`, `uploadedworkout_type`, `uploadedworkout_duration`) VALUES
(3, 'Extreme Legs', 'Heavy', '20'),
(6, 'bench', 'chest', '45'),
(7, 'bench', 'chest', '45'),
(8, 'bench', 'bxoqw', '45'),
(9, 'Bench Press', 'Chest', '45 MIn'),
(10, 'Deadlift', 'core', '60'),
(15, 'Extreme Arms', 'Medium', '25min'),
(16, 'Field Training', 'Heavy', '30min');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `f_name` varchar(100) NOT NULL,
  `l_name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `birthday` date NOT NULL,
  `password` varchar(100) NOT NULL,
  `image_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `f_name`, `l_name`, `username`, `email`, `birthday`, `password`, `image_path`) VALUES
(26, 'Abhijit', 'Singh Pradhan', 'abhijit', 'abhijit123@gmail.com', '2005-02-01', 'UopHzZ0eZnm46j1fQLe+JN60VMluxlXEAWLb45RBKkXaue8eL+UQedBHB1Ya8EM=', 'resources/images/profile/c7vtwaqft0b51.webp'),
(27, 'Amber', 'Rutledge', 'zohuqijo', 'hokomovaf@mailinator.com', '1997-12-10', 'ohUenThBU343xxIyFK9m4iA/Y3vJYBV5F+Yx6a5DXeOt49E7CzU2zwHkKq2LlQFdY9/niqo=', '/resources/images/default.webp'),
(28, 'Blythe', 'Kelly', 'midec', 'siveha@mailinator.com', '2004-01-07', '2aNN9YYJBKKYAwNzS6FK292LtwZHZdgruq/5oEXJCSpsdhHmBG0++GhTYmYMkWOD4plyAEE=', '/resources/images/default.webp'),
(29, 'Octavia', 'Salazar', 'pysaqoqer', 'sociqiky@mailinator.com', '1985-10-21', 'njiGkJ4gaPrdm0qRXC6S+bHEqqc0aR3myul4vp8MJkuGnL/5gkEJJz8SbMSeLeSfAQCXSdI=', '/resources/images/default.webp'),
(31, 'Hope', 'Sanchez', 'cawewa', 'gesan@mailinator.com', '2022-07-08', '7b9PRwuLHu8eMiHy+c4f4SSAkAk4XAZpFiiIGqKr09c0medVw4srpU0jL0AilwSgsZrF', '/resources/images/default.webp'),
(32, 'Tanner', 'Rutledge', 'jovocow', 'dyqogesup@mailinator.com', '1996-02-05', 'b4FXyJS0vsZ2dtrmr6S6MhZ0RcUI3omXWt+5GKt8aF1olGoOCsG2tfvMCRT6cmSkR7cLm5E=', '/resources/images/default.webp'),
(33, 'Rhonda', 'Coleman', 'tycamyc', 'zumod@mailinator.com', '1975-11-15', 'XbiOuoF5IOgGAFel2pPBppgoC2hB3aABcrPuOe0LihKhXQJdXIySTwJTmk9uRZ3q7LwIyjE=', '/resources/images/default.webp'),
(34, 'Petra', 'Gates', 'wubynu', 'lybuxad@mailinator.com', '2004-10-01', 'yNN9FRTlAsu/Unn/pnXnoyWRHUFqWuFK3d+p++iLfaZhtx8ZmjUcq57r5WFg5Qcy5HAybuM=', '/resources/images/default.webp'),
(35, 'Halee', 'Calderon', 'bacemac', 'ciqixik@mailinator.com', '1995-11-13', 'a5/7Y/sKzIUFwUYCTHrjUaHXv3Meqi8zA/bUpwv2Gs/EPvhOMUZ3yGJLPxv0Mfd0taXsIEc=', '/resources/images/default.webp'),
(36, 'Madeline', 'Byers', 'rakahenecu', 'mobov@mailinator.com', '2003-02-02', 'DijM2Ce3Oqy8aNpBS53pTIdl3B7Qp3wPeTarwvEqiJ30gEBsUozpSUhxnROQB8fSgD3GTrw=', '/resources/images/default.webp'),
(37, 'Bernard', 'Hardy', 'xuduniqi', 'cupyh@mailinator.com', '1994-02-22', 'S4cpiW+THiAzlQDW9HdwYBYNlCECZII+5EoL364t0SV6G+L6xtqO48yxf343yTDmDB3staQ=', '/resources/images/default.webp'),
(38, 'Nobel', 'Kumar ', 'nobel', 'nobel@gmail.com', '2004-06-10', '0XbJG2oX2mL9jwjYjUwlqhrYM6GxTQQb4sHSbDAUUjhx3+5SKqvcG2MYKEaXdUI=', '/resources/images/default.webp'),
(39, 'Pranish', 'Chaulagain', 'pranish', 'gorafozit@mailinator.com', '2020-06-23', '6JXrfghCen+ibzo/mHVA75LQZSNoRgCVt8Coul/fL862QERkQK9vvnMp1jtcEYQ=', '/resources/images/default.webp'),
(40, 'Kaushal', 'Raj Thapa', 'kaushal', 'bugeqe@mailinator.com', '1985-03-01', '3LnZbl0vAkj7vfJf6+hYk2MKzBgwV7RvQrKDmn7UOOI7ZlsatWmedO+BAH05Xtc=', '/resources/images/default.webp'),
(41, 'Aashutosh', 'Chalise', 'aashutosh', 'a@gmail.com', '2004-06-22', 'K9022EnofWB/zkDLzXsdzBLlGvWcy/IFI1WAdadGM8uGLIiKeQYssZ3nLM4Vd/I=', '/resources/images/default.webp'),
(42, 'Samik', 'Bhandari', 'samik', 'hurinede@mailinator.com', '1990-02-17', 'djz1FNZnYLkBm4Y2m76Cp+thLDbiLG+MW32CLf58t0s/JT/Y8jR3fsZEO2/lrgs=', '/resources/images/default.webp');

-- --------------------------------------------------------

--
-- Table structure for table `water_intake`
--

CREATE TABLE `water_intake` (
  `user_id` int(11) NOT NULL,
  `intake_date` date NOT NULL,
  `litres` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `water_intake`
--

INSERT INTO `water_intake` (`user_id`, `intake_date`, `litres`) VALUES
(26, '2025-05-21', 0),
(26, '2025-05-22', 1);

-- --------------------------------------------------------

--
-- Table structure for table `workout`
--

CREATE TABLE `workout` (
  `workout_id` int(11) NOT NULL,
  `workout_name` varchar(100) NOT NULL,
  `workout_type` varchar(100) NOT NULL,
  `workout_duration` int(11) NOT NULL,
  `workout_weight_lifted` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `workout`
--

INSERT INTO `workout` (`workout_id`, `workout_name`, `workout_type`, `workout_duration`, `workout_weight_lifted`, `user_id`) VALUES
(11, 'Push', 'Chest, Shoulder and triceps', 45, 0, 26);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`contact_id`);

--
-- Indexes for table `exercise`
--
ALTER TABLE `exercise`
  ADD PRIMARY KEY (`exercise_id`);

--
-- Indexes for table `exercise_workout`
--
ALTER TABLE `exercise_workout`
  ADD KEY `exercise_fk` (`exercise_id`),
  ADD KEY `workout_fk` (`workout_id`);

--
-- Indexes for table `favorite_meals`
--
ALTER TABLE `favorite_meals`
  ADD PRIMARY KEY (`favorite_id`),
  ADD UNIQUE KEY `unique_favorite` (`user_id`,`meal_id`),
  ADD KEY `meal_id` (`meal_id`);

--
-- Indexes for table `goals`
--
ALTER TABLE `goals`
  ADD PRIMARY KEY (`goal_id`),
  ADD KEY `user_goal_fk` (`user_id`);

--
-- Indexes for table `meal`
--
ALTER TABLE `meal`
  ADD PRIMARY KEY (`meal_id`);

--
-- Indexes for table `progress`
--
ALTER TABLE `progress`
  ADD PRIMARY KEY (`progress_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `progress_log`
--
ALTER TABLE `progress_log`
  ADD PRIMARY KEY (`log_id`),
  ADD UNIQUE KEY `unique_log` (`user_id`,`workout_id`,`exercise_id`,`log_date`),
  ADD KEY `workout_id` (`workout_id`),
  ADD KEY `exercise_id` (`exercise_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD KEY `roles_fk_user` (`user_id`);

--
-- Indexes for table `uploadedmeals`
--
ALTER TABLE `uploadedmeals`
  ADD PRIMARY KEY (`uploadedmeals_id`);

--
-- Indexes for table `uploadedworkout`
--
ALTER TABLE `uploadedworkout`
  ADD PRIMARY KEY (`uploadedworkout_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `water_intake`
--
ALTER TABLE `water_intake`
  ADD PRIMARY KEY (`user_id`,`intake_date`);

--
-- Indexes for table `workout`
--
ALTER TABLE `workout`
  ADD PRIMARY KEY (`workout_id`),
  ADD KEY `user_workout_fk` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `contact`
--
ALTER TABLE `contact`
  MODIFY `contact_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `exercise`
--
ALTER TABLE `exercise`
  MODIFY `exercise_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `favorite_meals`
--
ALTER TABLE `favorite_meals`
  MODIFY `favorite_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `goals`
--
ALTER TABLE `goals`
  MODIFY `goal_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `meal`
--
ALTER TABLE `meal`
  MODIFY `meal_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `progress`
--
ALTER TABLE `progress`
  MODIFY `progress_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=120;

--
-- AUTO_INCREMENT for table `progress_log`
--
ALTER TABLE `progress_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `uploadedmeals`
--
ALTER TABLE `uploadedmeals`
  MODIFY `uploadedmeals_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `uploadedworkout`
--
ALTER TABLE `uploadedworkout`
  MODIFY `uploadedworkout_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `workout`
--
ALTER TABLE `workout`
  MODIFY `workout_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `exercise_workout`
--
ALTER TABLE `exercise_workout`
  ADD CONSTRAINT `exercise_fk` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`exercise_id`),
  ADD CONSTRAINT `workout_fk` FOREIGN KEY (`workout_id`) REFERENCES `workout` (`workout_id`);

--
-- Constraints for table `favorite_meals`
--
ALTER TABLE `favorite_meals`
  ADD CONSTRAINT `favorite_meals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `favorite_meals_ibfk_2` FOREIGN KEY (`meal_id`) REFERENCES `meal` (`meal_id`);

--
-- Constraints for table `goals`
--
ALTER TABLE `goals`
  ADD CONSTRAINT `user_goal_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `progress`
--
ALTER TABLE `progress`
  ADD CONSTRAINT `progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `progress_log`
--
ALTER TABLE `progress_log`
  ADD CONSTRAINT `progress_log_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `progress_log_ibfk_2` FOREIGN KEY (`workout_id`) REFERENCES `workout` (`workout_id`),
  ADD CONSTRAINT `progress_log_ibfk_3` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`exercise_id`);

--
-- Constraints for table `roles`
--
ALTER TABLE `roles`
  ADD CONSTRAINT `roles_fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `water_intake`
--
ALTER TABLE `water_intake`
  ADD CONSTRAINT `water_intake_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `workout`
--
ALTER TABLE `workout`
  ADD CONSTRAINT `user_workout_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
