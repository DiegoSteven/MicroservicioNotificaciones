-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-06-2025 a las 14:25:22
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `notificationsdb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tc_device_notification`
--

CREATE TABLE `tc_device_notification` (
  `deviceid` int(11) NOT NULL,
  `notificationid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tc_group_notification`
--

CREATE TABLE `tc_group_notification` (
  `groupid` int(11) NOT NULL,
  `notificationid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `tc_group_notification`
--

INSERT INTO `tc_group_notification` (`groupid`, `notificationid`) VALUES
(1, 43);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tc_notifications`
--

CREATE TABLE `tc_notifications` (
  `id` int(11) NOT NULL,
  `type` varchar(128) NOT NULL,
  `attributes` varchar(4000) DEFAULT NULL,
  `always` bit(1) NOT NULL DEFAULT b'0',
  `calendarid` int(11) DEFAULT NULL,
  `notificators` varchar(128) DEFAULT NULL,
  `commandid` int(11) DEFAULT NULL,
  `description` varchar(4000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `tc_notifications`
--

INSERT INTO `tc_notifications` (`id`, `type`, `attributes`, `always`, `calendarid`, `notificators`, `commandid`, `description`) VALUES
(32, 'commandResult', '{}', b'1', NULL, 'web', NULL, 'Modificada Ariel Nuevaso edit'),
(33, 'commandResult', '{}', b'0', NULL, 'web', NULL, 'Modificada Ariel Nuevaso edit'),
(37, 'deviceOnline', '{}', b'0', NULL, 'web', NULL, NULL),
(39, 'deviceOnline', '{}', b'0', 1, 'web,command', 4, 'Guradadaso desde el front'),
(40, 'deviceInactive', '{}', b'1', NULL, 'web,mail', NULL, 'Front purasoooo mas api'),
(41, 'deviceOnline', '{}', b'1', NULL, 'web', NULL, 'Diego1'),
(42, 'commandResult', '{}', b'1', NULL, 'command', 4, 'Actualizado'),
(43, 'deviceOnline', '{}', b'1', NULL, 'web', NULL, 'Actualizadon Cookie'),
(44, 'commandResult', '{}', b'1', 1, 'mail', NULL, 'adad'),
(45, 'deviceOnline', '{}', b'1', NULL, 'web,mail', NULL, 'Notificación de prueba Nuevo Ariel'),
(46, 'commandResult', '{}', b'1', 1, 'mail', NULL, 'ett'),
(47, 'commandResult', '{}', b'0', NULL, 'mail', NULL, NULL),
(49, 'deviceOnline', '{}', b'1', NULL, 'web,mail', NULL, 'No base'),
(50, 'deviceOnline', '{}', b'1', NULL, 'web,mail', NULL, 'No base 2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tc_user_notification`
--

CREATE TABLE `tc_user_notification` (
  `userid` int(11) NOT NULL,
  `notificationid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `tc_user_notification`
--

INSERT INTO `tc_user_notification` (`userid`, `notificationid`) VALUES
(2, 39),
(2, 40),
(2, 41),
(2, 42),
(2, 43),
(2, 49),
(2, 50);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tc_device_notification`
--
ALTER TABLE `tc_device_notification`
  ADD KEY `fk_device_notification_deviceid` (`deviceid`),
  ADD KEY `fk_device_notification_notificationid` (`notificationid`);

--
-- Indices de la tabla `tc_group_notification`
--
ALTER TABLE `tc_group_notification`
  ADD KEY `fk_group_notification_groupid` (`groupid`),
  ADD KEY `fk_group_notification_notificationid` (`notificationid`);

--
-- Indices de la tabla `tc_notifications`
--
ALTER TABLE `tc_notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_notification_calendar_calendarid` (`calendarid`),
  ADD KEY `fk_notifications_commandid` (`commandid`);

--
-- Indices de la tabla `tc_user_notification`
--
ALTER TABLE `tc_user_notification`
  ADD KEY `fk_user_notification_notificationid` (`notificationid`),
  ADD KEY `fk_user_notification_userid` (`userid`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tc_notifications`
--
ALTER TABLE `tc_notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tc_device_notification`
--
ALTER TABLE `tc_device_notification`
  ADD CONSTRAINT `fk_device_notification_notificationid` FOREIGN KEY (`notificationid`) REFERENCES `tc_notifications` (`id`);

--
-- Filtros para la tabla `tc_group_notification`
--
ALTER TABLE `tc_group_notification`
  ADD CONSTRAINT `fk_group_notification_notificationid` FOREIGN KEY (`notificationid`) REFERENCES `tc_notifications` (`id`);

--
-- Filtros para la tabla `tc_user_notification`
--
ALTER TABLE `tc_user_notification`
  ADD CONSTRAINT `fk_user_notification_notificationid` FOREIGN KEY (`notificationid`) REFERENCES `tc_notifications` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
